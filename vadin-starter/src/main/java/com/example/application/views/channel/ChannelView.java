package com.example.application.views.channel;


import com.example.application.chat.ChatService;
import com.example.application.chat.Message;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import reactor.core.Disposable;

import java.util.ArrayList;
import java.util.List;

@Route("channel")
public class ChannelView extends VerticalLayout implements HasUrlParameter<String> {
    private final ChatService chatService;
    private final MessageList messageList;
    private final List<Message> receivedMessages = new ArrayList<>();

    private String channelId;

    public ChannelView(ChatService chatService) {
        this.chatService = chatService;

        setSizeFull();

        this.messageList = new MessageList(this.receivedMessages.stream().map(this::convert).toList());

        messageList.setSizeFull();
        add(messageList);

        MessageInput messageInput = new MessageInput(e -> sendMessage(e.getValue()));
        messageInput.setWidthFull();
        add(messageInput);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        if (chatService.channel(s).isEmpty()) {
            throw new IllegalArgumentException("Invalid channel id");
        }

        this.channelId = s;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        var subscription = subscribe();
        addDetachListener(event -> subscription.dispose());
    }

    private void sendMessage(String message) {
        if (!message.isBlank()) chatService.postMessage(this.channelId, message);
    }

    private void receiveMessages(List<Message> incoming) {
        getUI().ifPresent(ui -> ui.access(() -> {
            receivedMessages.addAll(incoming);
            messageList.setItems(receivedMessages.stream().map(this::convert).toList());
        }));
    }

    private MessageListItem convert(Message message) {
        return new MessageListItem(
                message.message(),
                message.timestamp(),
                message.author()
        );
    }

    private Disposable subscribe() {
        var subscription = chatService
                .liveMessages(channelId)
                .subscribe(this::receiveMessages);
        return subscription;
    }
}
