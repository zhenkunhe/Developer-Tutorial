from sanic import Sanic
from sanic.response import json
from sanic.response import text

app = Sanic(__name__)

@app.middleware
async def print_on_request(request):
    print("I print when a request is received by the server")

@app.middleware('response')
async def print_on_response(request, response):
    print("I print when a response is returned by the server")

@app.middleware('request')
async def halt_request(request):
    return text('I halted the request')

@app.middleware('response')
async def halt_response(request, response):
    return text('I halted the response')

@app.route('/')
async def handler(request):
    return text('I would like to speak now please')

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8000)
