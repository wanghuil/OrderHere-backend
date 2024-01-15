variable "region" {
  type    = string
  default = "us-east-1"
}

variable "dynamodb" {
  type    = string
  default = "terraform-state-lock-dynamodb-orderhere-backend"
}

variable "POSTGRES_URL" {
  description = "URL of the PostgreSQL database"
  type        = string
}

variable "POSTGRES_USERNAME" {
  description = "Username for the PostgreSQL database"
  type        = string
}

variable "POSTGRES_PASSWORD" {
  description = "Password for the PostgreSQL database"
  type        = string
  sensitive   = true
}

variable "ECR_IMAGE" {
  description = "ecr docker image url"
  type        = string
}

variable "r53_zone_id" {
  type    = string
}

variable "r53_record_name" {
  type    = string
}

variable "environment" {
  type    = string
  default = "dev"
}

variable "cluster_name" {
  type    = string
  default = "java-cluster"
}

variable "cluster_version" {
  type    = string
  default = "1.27"
}

variable "vpc_cidr" {
  type    = string
  default = "10.0.0.0/16"
}

variable "private_subnet_1" {
  type    = string
  default = "10.0.1.0/24"
}

variable "private_subnet_2" {
  type    = string
  default = "10.0.2.0/24"
}

variable "private_subnet_3" {
  type    = string
  default = "10.0.3.0/24"
}

variable "public_subnet_1" {
  type    = string
  default = "10.0.101.0/24"
}

variable "public_subnet_2" {
  type    = string
  default = "10.0.102.0/24"
}

variable "public_subnet_3" {
  type    = string
  default = "10.0.103.0/24"
}

variable "instance_types" {
  type    = string
  default = "t3.medium"
}

variable "csi_driver" {
  type    = string
  default = "v1.15.1-eksbuild.1"
}

variable "alb_ingress" {
  type    = string
  default = "1.4.8"
}

variable "external_dns" {
  type    = string
  default = "6.14.3"
}

variable "alb_ingress_image_tag" {
  type    = string
  default = "v2.4.7"
}

variable "alb_dns_name" {
  type    = string
}

variable "alb_zone_id" {
  type    = string
}

variable "PREFIX" {
  type    = string
}

variable "enable_fluentbit" {
  type        = bool
  description = "Boolean to enable fluentbit"
  default = true
}

variable "log_retention_in_days" {
  description = "Number of days to retain log events"
  type        = number
  default     = 7
}