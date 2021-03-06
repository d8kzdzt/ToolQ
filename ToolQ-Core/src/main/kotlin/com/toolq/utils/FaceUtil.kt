package com.toolq.utils

import com.toolq.helper.server.ToolQServer
import java.util.concurrent.ConcurrentHashMap

class FaceUtil {
    companion object {
        private val cache_facesV2 = mapOf<Int, String>(
            0 to "[惊讶]",
            1 to "[撇嘴]",
            2 to "[色]",
            3 to "[发呆]",
            4 to "[得意]",
            5 to "[流泪]",
            6 to "[害羞]",
            7 to "[闭嘴]",
            8 to "[睡]"
        )

        // 缓存表情集，如果QQ突然更新即从服务器获取
        private val cache_faces = ConcurrentHashMap<Int, String>().also {
            it[0] = "[惊讶]"
            it[1] = "[撇嘴]"
            it[2] = "[色]"
            it[3] = "[发呆]"
            it[4] = "[得意]"
            it[5] = "[流泪]"
            it[6] = "[害羞]"
            it[7] = "[闭嘴]"
            it[8] = "[睡]"
            it[9] = "[大哭]"
            it[10] = "[尴尬]"
            it[11] = "[发怒]"
            it[12] = "[调皮]"
            it[13] = "[呲牙]"
            it[14] = "[微笑]"
            it[15] = "[难过]"
            it[16] = "[酷]"
            it[18] = "[抓狂]"
            it[19] = "[吐]"
            it[20] = "[偷笑]"
            it[21] = "[可爱]"
            it[22] = "[白眼]"
            it[23] = "[傲慢]"
            it[24] = "[饥饿]"
            it[25] = "[困]"
            it[26] = "[惊恐]"
            it[27] = "[流汗]"
            it[28] = "[憨笑]"
            it[29] = "[悠闲]"
            it[30] = "[奋斗]"
            it[31] = "[咒骂]"
            it[32] = "[疑问]"
            it[33] = "[嘘]"
            it[34] = "[晕]"
            it[35] = "[折磨]"
            it[36] = "[衰]"
            it[37] = "[骷髅]"
            it[38] = "[敲打]"
            it[39] = "[再见]"
            it[41] = "[发抖]"
            it[42] = "[爱情]"
            it[43] = "[跳跳]"
            it[46] = "[猪头]"
            it[49] = "[拥抱]"
            it[53] = "[蛋糕]"
            it[54] = "[闪电]"
            it[55] = "[炸弹]"
            it[56] = "[刀]"
            it[57] = "[足球]"
            it[59] = "[便便]"
            it[60] = "[咖啡]"
            it[61] = "[饭]"
            it[63] = "[玫瑰]"
            it[64] = "[凋谢]"
            it[66] = "[爱心]"
            it[67] = "[心碎]"
            it[69] = "[礼物]"
            it[74] = "[太阳]"
            it[75] = "[月亮]"
            it[76] = "[赞]"
            it[77] = "[踩]"
            it[78] = "[握手]"
            it[79] = "[胜利]"
            it[85] = "[飞吻]"
            it[86] = "[怄火]"
            it[89] = "[西瓜]"
            it[96] = "[冷汗]"
            it[97] = "[擦汗]"
            it[98] = "[抠鼻]"
            it[99] = "[鼓掌]"
            it[100] = "[丑大了]"
            it[101] = "[坏笑]"
            it[102] = "[左哼哼]"
            it[103] = "[右哼哼]"
            it[104] = "[哈欠]"
            it[105] = "[鄙视]"
            it[106] = "[委屈]"
            it[107] = "[快哭了]"
            it[108] = "[阴险]"
            it[109] = "[亲亲]"
            it[110] = "[吓]"
            it[111] = "[可怜]"
            it[112] = "[菜刀]"
            it[113] = "[啤酒]"
            it[114] = "[篮球]"
            it[115] = "[兵乓]"
            it[116] = "[示爱]"
            it[117] = "[瓢虫]"
            it[118] = "[抱拳]"
            it[119] = "[勾引]"
            it[120] = "[拳头]"
            it[121] = "[差劲]"
            it[122] = "[爱你]"
            it[123] = "[NO]"
            it[124] = "[OK]"
            it[125] = "[转圈]"
            it[126] = "[磕头]"
            it[127] = "[回头]"
            it[128] = "[跳绳]"
            it[129] = "[挥手]"
            it[130] = "[激动]"
            it[131] = "[街舞]"
            it[132] = "[献吻]"
            it[133] = "[左太极]"
            it[134] = "[右太极]"
            it[136] = "[双喜]"
            it[137] = "[鞭炮]"
            it[138] = "[灯笼]"
            it[140] = "[K歌]"
            it[144] = "[喝彩]"
            it[145] = "[祈祷]"
            it[146] = "[爆筋]"
            it[147] = "[棒棒糖]"
            it[148] = "[喝奶]"
            it[151] = "[飞机]"
            it[158] = "[钞票]"
            it[168] = "[药]"
            it[169] = "[手枪]"
            it[171] = "[茶]"
            it[172] = "[眨眼睛]"
            it[173] = "[泪奔]"
            it[174] = "[无奈]"
            it[175] = "[卖萌]"
            it[176] = "[小纠结]"
            it[177] = "[喷血]"
            it[178] = "[斜眼笑]"
            it[179] = "[doge]"
            it[180] = "[惊喜]"
            it[181] = "[骚扰]"
            it[182] = "[笑哭]"
            it[183] = "[我最美]"
            it[184] = "[河蟹]"
            it[185] = "[羊驼]"
            it[187] = "[幽灵]"
            it[188] = "[蛋]"
            it[190] = "[菊花]"
            it[192] = "[红包]"
            it[193] = "[大笑]"
            it[194] = "[不开心]"
            it[197] = "[冷漠]"
            it[198] = "[呃]"
            it[199] = "[好棒]"
            it[200] = "[拜托]"
            it[201] = "[点赞]"
            it[202] = "[无聊]"
            it[203] = "[托脸]"
            it[204] = "[吃]"
            it[205] = "[送花]"
            it[206] = "[害怕]"
            it[207] = "[花痴]"
            it[208] = "[小样儿]"
            it[210] = "[飙泪]"
            it[211] = "[我不看]"
            it[212] = "[托腮]"
            it[214] = "[啵啵]"
            it[215] = "[糊脸]"
            it[216] = "[拍头]"
            it[217] = "[扯一扯]"
            it[218] = "[舔一舔]"
            it[219] = "[蹭一蹭]"
            it[220] = "[拽炸天]"
            it[221] = "[顶呱呱]"
            it[222] = "[抱抱]"
            it[223] = "[暴击]"
            it[224] = "[开枪]"
            it[225] = "[撩一撩]"
            it[226] = "[拍桌]"
            it[227] = "[拍手]"
            it[228] = "[恭喜]"
            it[229] = "[干杯]"
            it[230] = "[嘲讽]"
            it[231] = "[哼]"
            it[232] = "[佛系]"
            it[233] = "[掐一掐]"
            it[234] = "[惊呆]"
            it[235] = "[颤抖]"
            it[236] = "[啃头]"
            it[237] = "[偷看]"
            it[238] = "[扇脸]"
            it[239] = "[原谅]"
            it[240] = "[喷脸]"
            it[241] = "[生日快乐]"
            it[242] = "[头撞击]"
            it[243] = "[甩头]"
            it[244] = "[扔狗]"
            it[245] = "[加油必胜]"
            it[246] = "[口罩护体]"
            it[260] = "[搬砖中]"
            it[261] = "[忙到飞起]"
            it[262] = "[脑壳疼]"
            it[263] = "[沧桑]"
            it[264] = "[捂脸]"
            it[265] = "[辣眼睛]"
            it[266] = "[哦哟]"
            it[267] = "[头秃]"
            it[268] = "[问号脸]"
            it[269] = "[暗中观察]"
            it[270] = "[emm]"
            it[271] = "[吃瓜]"
            it[272] = "[呵呵哒]"
            it[277] = "[旺旺]"
            it[273] = "[我酸了]"
            it[274] = "[太难了]"
            it[278] = "[汉]"
            it[279] = "[打脸]"
            it[280] = "[击掌]"
            it[281] = "[无眼笑]"
            it[282] = "[敬礼]"
            it[283] = "[狂笑]"
            it[284] = "[面无表情]"
            it[285] = "[摸鱼]"
            it[286] = "[魔鬼笑]"
            it[287] = "[哦]"
            it[288] = "[请]"
            it[289] = "[睁眼]"
        }

        @JvmStatic
        fun check(id : Int) : String? {
            return if(cache_faces.containsKey(id))
                cache_faces[id]
            else {
                val server = ToolQServer.create("Main.checkFace")
                val response = server.request()
                if(response.ret == 200) {
                    val data = response.data.get("name").asString
                    addFace(id, data)
                } else {
                    "[未知]"
                }
            }
        }

        @JvmStatic
        fun addFace(id : Int, name : String) : String {
            cache_faces[id] = name
            return name
        }

        @JvmStatic
        fun exitsFace(id: Int) : Boolean = cache_faces.containsKey(id)
    }
}