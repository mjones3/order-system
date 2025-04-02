#!/usr/bin/env bash
set -e

# Define a single ECR repository URL for all services
ECR_REPO_URL="294417223953.dkr.ecr.us-east-1.amazonaws.com/order-system-repo"

# List the service folders you want to build
services=("order-service" "inventory-service" "payment-service")

# Use the first command-line argument as the image tag, defaulting to "latest"
IMAGE_TAG=${1:-"latest"}

# Log in to ECR once (common for all images)
echo "Logging into ECR..."
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin "${ECR_REPO_URL}"

# Loop through each service and build & push its Docker image
for service in "${services[@]}"; do
  # Create a unique image tag per service: e.g., order-service-latest, inventory-service-latest, etc.
  IMAGE_FULL_TAG="${ECR_REPO_URL}:${service}-${IMAGE_TAG}"
  
  echo "-------------------------------------------------------"
  echo "Building Docker image for $service with tag $IMAGE_FULL_TAG..."
  # docker build -t "${IMAGE_FULL_TAG}" "./apps/${service}"
  # docker build --platform linux/arm64 -t "${IMAGE_FULL_TAG}" "./apps/${service}"
  
  docker build --platform linux/amd64 -t "${IMAGE_FULL_TAG}" "./apps/${service}"
  echo "Pushing Docker image for $service..."
  docker push "${IMAGE_FULL_TAG}"
  echo "-------------------------------------------------------"
done

# Now run Terraform, passing each service's image URI as a variable.
cd terraform
terraform init
terraform apply \
  -var="order_service_image=${ECR_REPO_URL}:order-service-${IMAGE_TAG}" \
  -var="inventory_service_image=${ECR_REPO_URL}:inventory-service-${IMAGE_TAG}" \
  -var="payment_service_image=${ECR_REPO_URL}:payment-service-${IMAGE_TAG}" \
  -auto-approve
