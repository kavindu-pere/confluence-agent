// src/widget/index.tsx
import ReactDOM from 'react-dom';
import { VerticalLayout } from "@vaadin/react-components/VerticalLayout";
import { MessageInput } from "@vaadin/react-components/MessageInput";
import MessageList from "../../../src/main/frontend/components/MessageList";
import { useState } from "react";
import { MessageItem } from "../../../src/main/frontend/types/MessageItem";
import { nanoid } from "nanoid";

// API service for extension
const ExtensionAgentService = {
  chat: async (chatId: string, message: string) => {
    // Replace with extension's API endpoint
    const response = await fetch('http://localhost:8080/api/chat', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ chatId, message })
    });
    return response.json();
  }
};

function ConfluenceAgentWidget() {
  const [chatId] = useState(nanoid());
  const [working, setWorking] = useState(false);
  const [messages, setMessages] = useState<MessageItem[]>([{
    role: 'agent',
    content: 'Welcome to Confluence Chat! How can I help you?'
  }]);

  async function sendMessage(message: string) {
    setWorking(true);
    addMessage({
      role: 'user',
      content: message
    });

    try {
      const response = await ExtensionAgentService.chat(chatId, message);
      addMessage({
        role: 'agent',
        content: response.content
      });
    } catch (error) {
      console.error(error);
    } finally {
      setWorking(false);
    }
  }

  function addMessage(message: MessageItem) {
    setMessages(messages => [...messages, message]);
  }

  return (
    <VerticalLayout className="h-full" theme="spacing padding">
      <div className="flex flex-col gap-m p-m box-border h-full">
        <h1>Confluence Agent</h1>
        <MessageList messages={messages} className="flex-grow overflow-scroll" />
        <MessageInput onSubmit={e => sendMessage(e.detail.value)} disabled={working}/>
      </div>
    </VerticalLayout>
  );
}

ReactDOM.render(<ConfluenceAgentWidget />, document.getElementById('root'));