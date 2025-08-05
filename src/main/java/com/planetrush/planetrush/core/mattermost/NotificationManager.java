package com.planetrush.planetrush.core.mattermost;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationManager {

	private final MattermostSender sender;

	public void sendNotification(Exception e, String uri, String params) {
		log.info("Send Mattermost Notification");
		sender.sendMessage(e, uri, params);
	}

}
