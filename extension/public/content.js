// public/content.js
function createWidget() {
    const container = document.createElement('div');
    container.id = 'confluence-chat-widget';
    container.style.cssText = `
      position: fixed;
      bottom: 20px;
      right: 20px;
      width: 400px;
      height: 600px;
      background: white;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.1);
      z-index: 10000;
      overflow: hidden;
    `;
  
    const iframe = document.createElement('iframe');
    iframe.src = chrome.runtime.getURL('widget.html');
    iframe.style.cssText = 'width: 100%; height: 100%; border: none;';
    
    container.appendChild(iframe);
    document.body.appendChild(container);
  }
  
  createWidget();