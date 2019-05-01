from flask import Flask, request
from flask_restful import Api, Resource, reqparse
import json

app = Flask(__name__)
api = Api(app)

class User(Resource):
    def post(self):
        payload = json.loads(request.data)
        print(payload)
        result = {
            "itemName" : "Phone",
            "products":[]
        }

        for i in range(0,10):
            result["products"].append({
                "name":"product"+str(i),
                "price":"25",
                "count":0,
                "imageUrl":"dasdasd",
                "id":i
            })
        return result,200
    def get(self):
        return "get call",200

class list(Resource):
    def post(self):
        payload = json.loads(request.data)
        print(payload)
        return {"payload":{
            "id":"12345"
        }},200
    def get(self):
        result = {
            "itemName": "Phone",
            "products": []
        }

        for i in range(0, 10):
            result["products"].append({
                "name": "product" + str(i),
                "price": "25",
                "count": 0,
                "imageUrl": "dasdasd",
                "id": i
            })
        return result, 200

api.add_resource(User,"/user/api/fetchpoduct")
api.add_resource(list,"/user/api/list")

app.run(host='0.0.0.0',port=5000)