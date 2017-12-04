from sanic import Sanic
from sanic.response import json
from sanic.response import text
from sanic.response import html

app = Sanic(__name__)

@app.route('/tag/<tag>')
async def tag_handler(request, tag):
    return text('Tag - {}'.format(tag))

@app.route('/number/<integer_arg:int>')
async def integer_handler(request, integer_arg):
    return text('Integer - {}'.format(integer_arg))

@app.route('/number/<number_arg:number>')
async def number_handler(request, number_arg):
    return text('Number - {}'.format(number_arg))

@app.route('/person/<name:[A-z]>')
async def person_handler(request, name):
    return text('Person - {}'.format(name))

@app.route('/folder/<folder_id:[A-z0-9]{0,4}>')
async def folder_handler(request, folder_id):
    return text('Folder - {}'.format(folder_id))

@app.route('/post',methods=['POST'])
async def post_handler(request):
    return text('POST request - {}'.format(request.json))

@app.route('/get',methods=['GET'])
async def GET_handler(request):
    return text('GET request - {}'.format(request.args))

@app.route("/",methods=['GET', 'POST'])
async def test(request):
    if request.method == 'GET':
        return html('<form action="/test" method="post"><input type="submit" value="Send" /></form>')
    elif request.method == 'POST':
        return text("OK this is a post method")
    else:
        return ("ok")
    return json({ "hello": "world" })

app.run(host="0.0.0.0", port=8000)