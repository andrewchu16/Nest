import openai
from flask import Flask, request, jsonify
import gradio as gr

openai.apikey = "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" #api here

messages = [{"role": "system", "content": "At the beginning, say that your name is Nest help bot and You are a technical support expert that specializes in being the technical support for the company Nest which is responsible for school organizations networking with outside organizations the example prompts you should be able to output are (input e.g. How do I create an organization), (the output navigate to the my organizations tab, press the create new organization button then fill out the necessary information needed to create an organization"}]

def chatwithopenai(userinput):
    messages.append({"role": "user", "content": userinput})
    response = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=messages
    )
    ChatGPTreply = response.choices[0].message.content
    messages.append({"role": "assistant", "content": ChatGPT_reply})
    return ChatGPT_reply

app = Flask(__name__)

#gradio interface function
def CustomChatGPT(user_input):
    return chat_with_openai(user_input)

#gradio app
demo = gr.Interface(fn=CustomChatGPT, inputs="text", outputs="text", title="Real Estate Pro")

#endpoint for gradio interface to communicate with Flask
@app.route('/api/chat', methods=['POST'])
def chat():
    data = request.get_json()
    user_message = data.get('message')
    chatbot_response = chat_with_openai(user_message)
    return jsonify({'response': chatbot_response})

#gradio's launch method integrates with Flask
@app.route('/')
def home():
    return demo.launch(share=True, server_name='0.0.0.0', server_port=5000, inline=True)

if __name == '__main':
    app.run(debug=True, port=5000)