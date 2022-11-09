import nltk
from nltk.chat.util import Chat, reflections

reflections = {
    "i am": "you are",
    "i was": "you were",
    "i": "you",
    "i'm": "you are",
    "i'd": "you would",
    "i've": "you have",
    "i'll": "you will",
    "my": "your",
    "you are": "I am",
    "you were": "I was",
    "you've": "I have",
    "you'll": "I will",
    "your": "my",
    "yours": "mine",
    "you": "me",
    "me": "you"
}

pairs = [
    [
        r"my name is (.*)",
        ["Hello %1, How are you today ?", ]
    ],
    [
        r"hi|hey|hello",
        ["Hello", "Hey there", 'hey wassup?']
    ],
    [
        r"who are you ?",
        ["I am a chatbot written by hrishikesh", ]
    ],
    [
        r"how are you ?",
        ["I'm doing goodnHow about You ?", ]
    ],
    [
        r"I am fine",
        ["Great to hear that, How can I help you?", ]
    ],

    [
        r"(.*) created ?",
        ["i was created using nltk library", ]
    ],
    [
        r"who (.*) created (.*)?",
        ["i was created by hrishikesh", ]
    ],
    [
        r"quit",
        ["Bye :) ",
            "It was nice talking to you. See you soon :)"]
    ],
]


print("Start talking with the bot (type quit to stop)!")
chat = Chat(pairs, reflections)
chat.converse()
