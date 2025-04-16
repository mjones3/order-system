def lambda_handler(event, context):
    # Process the incoming event (e.g. a request)
    print("Received event:", event)
    return {
        "statusCode": 200,
        "body": "Hello from Python Lambda!"
    }
