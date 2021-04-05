package com.toolq.helper.bot

import com.toolq.ToolQManager.getBot

@ExperimentalUnsignedTypes
abstract class BotEvent(botUin: Long) : Center(getBot(botUin)!!), IBotEvent {

}