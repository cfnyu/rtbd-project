import os
from flask import Flask, jsonify, request, render_template

app = Flask(__name__)
topic_dict = {}

with open('output-data.txt') as f:
    for line in f:
        values = line.split(':')
        date_range = values[0]
        topic = values[1]
        so_ids = values[2]

        if date_range not in topic_dict:
            topic_dict[date_range] = [{topic: so_ids}]

        topic_dict[date_range].append({topic: so_ids})
@app.route('/')
def index():
    # return jsonify(gropued_date_dict=topic_dict), 200
    return render_template('results.html', grouped_date_dict=topic_dict)

@app.route('/<date>')
def filter_by_date(date):
    return render_template('results.html', grouped_date_dict=topic_dict, topics=topic_dict[date], selected_date=date)  

@app.route('/<date>/<topic>')
def filter_by_topic(date, topic):
    for topic_date in topic_dict[date]:
        if topic_date.keys()[0] == topic:
            ids =  topic_date.values()[0].split(",")
            return render_template('results.html', grouped_date_dict=topic_dict, topics=topic_dict[date], values=ids, selected_date=date, selected_topic=topic)


if __name__ == "__main__":
    app.run()
