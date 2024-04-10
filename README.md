# OrderHere-backend

## Overview
This document provides a high-level overview of the architecture for our system. The design is focused on ensuring scalability, reliability, and efficiency across all aspects of the application.

## Architecture

The following architecture diagram illustrates the high-level structure of our system:

![Architecture Diagram](pics/architecture-diagram.png)

### Frontend

- **Vercel**: Our frontend is deployed on Vercel for serverless hosting, providing seamless scaling and a robust deployment pipeline.
- **GitHub Repository**: The source code for the frontend is maintained in a GitHub repository with automated deployments to Vercel upon each push to the main branch.

### DNS Management

- **Route 53**: We use Amazon Route 53 for DNS management, which provides reliable and scalable routing to our frontend and backend services.

### Backend

- **Amazon EKS (Elastic Kubernetes Service)**: Backend services are managed and orchestrated through Kubernetes within EKS, providing high availability and scalability.
- **Application Load Balancer**: An ALB distributes incoming traffic among the backend services, improving fault tolerance and load balancing.
- **Prometheus & Grafana**: For monitoring purposes, Prometheus collects metrics while Grafana offers powerful visualization tools.
- **Amazon S3**: We leverage S3 for storing application assets and managing backups securely.
- **Velero**: Velero is used for disaster recovery, backing up and restoring our Kubernetes cluster resources when necessary.

### Logging and Monitoring

- **CloudWatch**: AWS CloudWatch is instrumental for logging and monitoring, giving us detailed insights into application performance and health.
- **Fluentbit**: As an open-source log processor and forwarder, Fluentbit allows us to collect and send logs to multiple destinations efficiently.
- **K8s Lens**: K8s Lens is our Kubernetes IDE of choice, making the management of clusters and workloads more straightforward.

### CI/CD

- **GitHub Actions**: Our CI/CD pipeline is powered by GitHub Actions, automating our workflows from code to production.

### Infrastructure Management

- **AWS Fargate**: Fargate offers serverless compute for containers, freeing us from the overhead of managing servers or clusters.
- **AWS Auto Scaling**: To manage the load efficiently, AWS Auto Scaling adjusts the number of EC2 instances as needed.

### Container Registry

- **Amazon ECR (Elastic Container Registry)**: ECR provides a managed Docker container registry that simplifies the storing, management, and deployment process for our container images.


## Accessing API Documentation

Once the application is running, you can access the API documentation at the following address:

[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)





