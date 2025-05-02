import os
import json
import logging
import urllib.request
import urllib.error

# Use the Lambda’s built‑in logger
logger = logging.getLogger()
logger.setLevel(logging.INFO)

# This env var must be configured in your Lambda’s settings
API_ENDPOINT = os.environ["API_ENDPOINT_INVENTORY"]  


def lambda_handler(event, context):
    """
    event: either
      {"items": [ {"itemNumber":"A1","quantity":2}, ... ] }
    or directly:
      [ {"itemNumber":"A1","quantity":2}, ... ]
    """
    # logger.info("--------")
    # logger.info("Received event: %s", event)
    # input = event["input"]
    # payload_list = input["items"]

    # Normalize payload
    # if isinstance(event, dict) and "items" in input:
    #     payload_list = input["items"]
    #     logger.info("payload_list: %s", payload_list)
    # elif isinstance(event, list):
    #     payload_list = event
    # else:
    #     # Unexpected shape
    #     msg = "Event must be a list or dict with 'items' key"
    #     logger.error(msg)
    #     return {"statusCode":400, "body": msg}

    # # Wrap in an object if your downstream expects it
    # body_dict = {"items": payload_list}

    # 1) Drill into the nested keys to get the raw JSON string

    body_str = event['input']['Payload']['body']

    # 2) Parse it into a Python dict (null → None, numbers → int/float, etc.)
    parsed_body = json.loads(body_str)


    url = "http://" + API_ENDPOINT + "/api/inventory"

    json_bytes = json.dumps(parsed_body).encode("utf-8")

    logger.info("Sending request to: %s", url)
    req = urllib.request.Request(
        url,
        data=json_bytes,
        headers={"Content-Type": "application/json"},
        method="POST"
    )

    try:
        with urllib.request.urlopen(req) as resp:
            resp_body = resp.read().decode("utf-8")
            status = resp.getcode()
            logger.info("Downstream returned %d: %s", status, resp_body)
            return {
                "statusCode": status,
                "body": resp_body
            }

    except urllib.error.HTTPError as e:
        error_text = e.read().decode("utf-8")
        logger.error("HTTPError %d: %s", e.code, error_text)
        return {
            "statusCode": e.code,
            "body": error_text
        }

    except Exception as e:
        logger.exception("Unexpected error calling downstream API")
        return {
            "statusCode": 500,
            "body": str(e)
        }
