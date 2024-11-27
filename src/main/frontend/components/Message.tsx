import { MessageProps } from "Frontend/types/MessageProps";
import Markdown from "react-markdown";

export default function Message({message}: MessageProps) {
    return (
        <div className="mb-1">
            <div className="font-bold">
                {message.role === 'user' ? '🧑‍💻 You' : '🤖 Samantha'}
            </div>
            <div>
                <Markdown>
                    {message.content}
                </Markdown>
            </div>
        </div>
    )
};