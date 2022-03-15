import json

from flask import Flask, jsonify

# flask setup & config
app = Flask(__name__)
app.config['JSON_SORT_KEYS'] = False
app.config['JSONIFY_PRETTYPRINT_REGULAR'] = True

@app.route("/Login/")
def test():
    return jsonify({
        "authToken": "124315235426",
        "userType": "student"
    })


if __name__ == "__main__":
    app.run()