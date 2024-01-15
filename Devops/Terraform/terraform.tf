terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
    }
  }
}

provider "aws" {
  region = var.region
}

#not allowed for variables
terraform {
  backend "s3" {
    encrypt        = true
    bucket         = "terraform-remote-state-storage-orderhere-backend"
    region         = "ap-southeast-2"
    key            = "./terraform.tfstate"
    profile        = "default"
    dynamodb_table = "terraform-state-lock-dynamodb-orderhere-backend"
  }
}