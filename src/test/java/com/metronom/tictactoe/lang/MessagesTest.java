package com.metronom.tictactoe.lang;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class MessagesTest {
    @Test
    public void get() {
        Messages messages = Messages.getInstance();
        MessageKey[] keys = MessageKey.values();

        for (MessageKey key : keys)
            assertNotNull(messages.get(key));
    }
}