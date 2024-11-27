import { VerticalLayout } from "@vaadin/react-components/VerticalLayout"
import { MessageInput } from "@vaadin/react-components/MessageInput";
import MessageList from "Frontend/components/MessageList";
import { useState } from "react";
import { MessageItem } from "Frontend/types/MessageItem";
import { AgentService } from "Frontend/generated/endpoints";
import { nanoid } from "nanoid";

export default function ConfluenceAgent() {
  const [chatId, setChatId] = useState(nanoid());
  const [working, setWorking] = useState(false);
  const [messages, setMessages] = useState<MessageItem[]>([{
    role: 'agent',
    content: 'Welcome to Confluence Chat! How can I help you today?'
  }]);

  async function sendMessage(message: string) {
    setWorking(true);
    addMessage({
      role: 'user',
      content: message
    });

    let first = true;
    AgentService.chat(chatId, message)
      .onNext(responseToken => {
        if (first && responseToken) {
          addMessage({
            role: 'agent',
            content: responseToken
          });
          first = false;
        } else {
          appendToLatestMessage(responseToken);
        }
      })
      .onError(() => setWorking(false))
      .onComplete(() => setWorking(false));
  }

  function addMessage(message: MessageItem) {
    setMessages(messages => [...messages, message]);
  }

  function appendToLatestMessage(chunk: string) {
    setMessages(messages => {
      const latestMessage = messages[messages.length - 1];
      latestMessage.content += chunk;
      return [...messages.slice(0, -1), latestMessage];
    });
  }

  return (
    <div style={{ position: 'relative', height: '100vh', width: '100%' }}>
      <div
        id="logo-div"
        style={{
          position: 'absolute',
          bottom: '20px',
          left: '20px',
          zIndex: 1000
        }}
      >
        <img
          src="images/logo.png"
          alt="Application Logo"
          style={{ maxWidth: '150px', height: 'auto' }}
        />
      </div>
      <VerticalLayout
        className="h-full"
        theme="spacing padding"
        style={{
          alignItems: 'center',
          height: '100vh',
          width: '100%'
        }}
      >
        <div className="flex flex-col gap-m p-m box-border h-full" style={{ width: '50%' }}>
          <h2>Samantha the Confluence Agent</h2>
          <MessageList messages={messages} className="flex-grow overflow-scroll" />
          <MessageInput onSubmit={e => sendMessage(e.detail.value)} className="px-0" disabled={working} />
        </div>
      </VerticalLayout>
    </div>
  );
}