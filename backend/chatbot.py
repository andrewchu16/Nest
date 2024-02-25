import openai
from flask import Flask, request, jsonify
import gradio as gr

openai.api_key = "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" #api here

messages = [{"role": "system", "content": "You are a technical support expert that specializes in helping users navigating the Nest website which helps organizations network with school departments"}]

def chat_with_openai(user_input):
    messages.append({"role": "user", "content": user_input})
    response = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=messages
    )
    ChatGPT_reply = response.choices[0].message.content
    messages.append({"role": "assistant", "content": ChatGPT_reply})
    return ChatGPT_reply

app = Flask(__name__)

#gradio interface function
def CustomChatGPT(user_input):
    return chat_with_openai(user_input)

#gradio app
demo = gr.Interface(fn=CustomChatGPT, inputs="text", outputs="text", title="Nest Technical Support")

#endpoint for gradio interface to communicate with Flask
@app.route('/api/chat', methods=['POST'])
def chat():
    data = request.get_json()
    user_message = data.get('message')
    chatbot_response = chat_with_openai(user_message)
    return jsonify({'response': chatbot_response})

#gradio's `launch` method integrates with Flask
@app.route('/')
def home():
    return demo.launch(share=True, server_name='0.0.0.0', server_port=5000, inline=True)

if __name__ == '__main__':
    app.run(debug=True, port=5000)

