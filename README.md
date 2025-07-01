# EC2 First Project – Comandos utilizados

Hola, soy Alejandro y aqui dejo paso a paso los comandos que me funcionaron para crear, desplegar y monitorear mi instancia EC2 con Docker y Tomcat, listo para que adjunte pantallazos. Si, lo hice mas bonito con ChatGPT porque solo queda una hora para hacer el resto y tengo que seguir trabajando ;_; pero esto resume los pasos que seguí entre comandos para ver el ciclo de vida de este proyecto

## 1. Preparar la clave SSH

```bash
chmod 400 mi-clave-ec2.pem
```

## 2. Configurar el Security Group

```bash
# Autorizar SSH (22) desde mi IP publica
aws ec2 authorize-security-group-ingress \
  --group-id sg-0a01c513a3443d630 \
  --protocol tcp --port 22 --cidr $(curl -s https://checkip.amazonaws.com)/32

# Autorizar Tomcat (8080) desde cualquier origen
aws ec2 authorize-security-group-ingress \
  --group-id sg-0a01c513a3443d630 \
  --protocol tcp --port 8080 --cidr 0.0.0.0/0
```

## 3. Definir region por defecto

```bash
aws configure set region us-east-2
```

## 4. Crear user-data para instalar Docker al arrancar

```bash
cat > user-data.sh << 'EOF'
#!/bin/bash
set -e
yum update -y
amazon-linux-extras install docker -y
systemctl enable docker
systemctl start docker
usermod -aG docker ec2-user
EOF
```

## 5. Seleccionar subnet publica

```bash
SUBNET_ID=$(aws ec2 describe-subnets \
  --filters Name=vpc-id,Values=vpc-0bbf306dbe389e548 Name=map-public-ip-on-launch,Values=true \
  --query 'Subnets[0].SubnetId' --output text)
echo "Subnet elegida: $SUBNET_ID"
```

## 6. Lanzar instancia EC2

```bash
INSTANCE_ID=$(aws ec2 run-instances \
  --image-id ami-05df0ea761147eda6 \
  --instance-type t2.micro \
  --count 1 \
  --key-name mi-clave-ec2 \
  --security-group-ids sg-0a01c513a3443d630 \
  --subnet-id $SUBNET_ID \
  --associate-public-ip-address \
  --monitoring Enabled=true \
  --user-data file://user-data.sh \
  --metadata-options HttpTokens=required,HttpPutResponseHopLimit=2 \
  --tag-specifications "ResourceType=instance,Tags=[{Key=Name,Value=EC2-FirstInstance}]" \
  --query 'Instances[0].InstanceId' --output text)
```

## 7. Verificar estado e IP publica

```bash
aws ec2 describe-instances \
  --instance-ids $INSTANCE_ID \
  --query 'Reservations[0].Instances[0].[State.Name,PublicIpAddress]' \
  --output table
```

## 8. Conectarme via SSH

```bash
ssh -i mi-clave-ec2.pem ec2-user@<IP_PUBLICA>
```

## 9. Instalar y configurar MariaDB

```bash
sudo yum install -y mariadb-server
sudo systemctl enable --now mariadb
sudo mysql -u root -e "CREATE DATABASE vehiculos;"
```

## 10. Compilar la aplicacion sin tests

```bash
./mvnw clean package -DskipTests
```

## 11. Verificar el WAR generado

```bash
ls -lh target/vehiculosBuild.war
```

## 12. Desplegar en Tomcat (Docker)

```bash
docker restart tomcat
```

## 13. Revisar logs del contenedor

```bash
docker logs tomcat | tail -n 20
```

## 14. Probar endpoint

```bash
curl -I http://<IP_PUBLICA>:8080/vehiculosBuild/api/vehiculos
```

---

> Para cada comando exitoso subo un pantallazo que lo muestre funcionando y listo para la entrega.

Si, fue creado con ChatGPT porque me queda 1 hora para entregar esta prueba y aun tengo otro trabajo que entregar ;_;
