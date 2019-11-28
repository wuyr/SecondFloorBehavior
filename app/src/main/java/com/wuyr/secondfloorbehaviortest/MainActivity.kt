package com.wuyr.secondfloorbehaviortest

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.wuyr.secondfloorbehavior.SecondFloorBehavior
import kotlinx.android.synthetic.main.act_main_view.*
import java.util.*


/**
 * @author wuyr
 * @github https://github.com/wuyr/SecondFloorBehavior
 * @since 2019-09-29 下午12:02
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main_view)
        setupStatusBar()
        init()
    }

    private lateinit var adapter: TextListAdapter
    private lateinit var secondFloorBehavior: SecondFloorBehavior

    private fun init() {
        secondFloorRecyclerView.adapter = ImageListAdapter(this)

        adapter = TextListAdapter(this, getRandomData(10).toMutableList())
        firstFloorRecyclerView.adapter = adapter
        refreshLayout.setOnRefreshListener {
            refreshLayout.postDelayed({
                refreshLayout.isRefreshing = false
                adapter.setData(*getRandomData(10))
            }, 1000)
        }
        //获取到Behavior实例
        secondFloorBehavior = (secondFloorView.layoutParams as
                CoordinatorLayout.LayoutParams).behavior as SecondFloorBehavior

        secondFloorBehavior.setOnStateChangeListener {
            state.text = when (it) {
                SecondFloorBehavior.STATE_NORMAL -> "静止"
                SecondFloorBehavior.STATE_DRAGGING -> "拖动中"
                SecondFloorBehavior.STATE_PREPARED -> "准备进入二楼"
                SecondFloorBehavior.STATE_OPENING -> "正在进入二楼"
                SecondFloorBehavior.STATE_OPENED -> "已进入二楼"
                SecondFloorBehavior.STATE_CLOSING -> "正在退出二楼"
                else -> ""
            }
        }

        secondFloorBehavior.setEnterAnimationInterpolator(DecelerateInterpolator())
        secondFloorBehavior.setExitAnimationInterpolator(DecelerateInterpolator())

    }

    fun onEnterSecondFloor() {
        progressBar.animate().alpha(0F).setDuration(secondFloorBehavior.enterDuration * 2).start()
    }

    fun onExitSecondFloor() {
        progressBar.animate().alpha(1F).setDuration(secondFloorBehavior.exitDuration * 2).start()
    }

    override fun onBackPressed() {
        if (secondFloorBehavior.state == SecondFloorBehavior.STATE_OPENED) {
            secondFloorBehavior.leaveSecondFloor()
        } else {
            super.onBackPressed()
        }
    }

    private val random = Random()

    private fun getRandomData(count: Int) = Array(count) {
        data[random.nextInt(data.size)]
    }

    private val data = arrayOf(
        "不到园林，怎知春色如许？原来姹紫嫣红开遍，似这般都付与断井颓垣。良辰美景奈何天，赏心乐事谁家院？ 朝飞暮卷，云霞翠轩，雨丝风片，烟波画船。\n《牡丹亭-游园惊梦》",
        "半遮面兒弄絳紗，暗飛桃紅泛赤霞。拾釵人會薄命花，釵貶洛陽價，落絮飛花辱了君清雅。\n《紫钗记-灯街拾翠》",
        "鴛盟初訂莫相猜，便似金堅難破壞。任天荒地老，莫折此紫鸞釵。苦相思，能買不能賣。\n《紫钗记-花院盟香》",
        "茫茫烟水觅芳踪，浅笑轻颦常入梦。烟疑粉坠，水像脂融，似叶扁舟幸得风吹送，喜见锦帆斜挂夕阳中，我是彩云卿是凤。今日天台有路，莫教雾蔽烟笼。虽是萍水相交，何忍化秋云春梦。\n《再世红梅记-观柳还琴》",
        "好一朵芙蓉出水百花羞，眉似苏堤春晓柳，盈盈秋水，为何轻罩雾烟愁。嗟莫是坐月怨秦箫，抑或是倚风寒翠袖。\n《帝女花-树盟》",
        "幽香一縷透輕紗，柳煙裙腰絲一把。翰墨有緣能相會，情苗愛葉早萌牙。一個是張儉無家，一個是雲英未嫁。拜過瑤池王母，為乞彩鳳隨鴉。相看無語各銷魂，禁不住心猿意馬。\n《紫钗记-花院盟香》",
        "百里遥遥辞帝京，一路绵绵情愈永，记否招郎投夜馆，鬼丛姊妹不同情，我推衾欲抱如花影，你笑指镜湖江上月，问何必拨影撩形。三十日露宿风餐，一百次嗔郎任性。\n《再世红梅记-焦窗魂合》",
        "春光满眼万花妍，三春景致何曾见。玉燕双双绕翠轩，蝶儿飞舞乐绵绵，乐绵绵，万花争吐艳。绿柳娇嫩，倚池畔随风曳展。心忧岁月变迁，一朝美艳化烟，叹春光易逝愁深牵。看牡丹亭畔，有花韆，且待我荡上东墙，唤取春回转。\n《牡丹亭-游园惊梦》",
        "谢宫主玉手赐琼浆，好比月破蓬莱，云迷楚岫。甘露未为奇，玉杯宁足罕，最难得是眉目暗相投。彩凤有翠拥红遮，经已兰麝微闻，我未接葡萄，香先透。翘首望瑶池，天上有金童玉女，人间亦有凤侣莺俦。忽见含樟树，倚殿千年，怎得情如合抱仝长寿。\n《帝女花-树盟》",
        "三千风雨满仙山，隐约现情帆。爱深渐疏淡任风吹散。绾结三生一朝分散。红楼孽满，梦己断厌尘寰。剩有泪痕，付与鹃雁。魂在故园，暂寄潇湘不散。看宝玉痛孤单，惨切唤魂还，天宫泣幽咽，人间空嗟叹。往事应了，爱恨怕翻。弦断再续，也觉空泛。\n《红楼梦-幻觉离恨天》",
        "驚見閨秀桃紅暗泛。好似天際玄娘變幻。一笑回眸遞送秋波冷。託生風月壇。不差一絲間，定是玄女偷偷降落降落世間。化春鶯柳巷憐翠雁。失去廟堂夢裡顏，我夢覺常自嘆，痛泣孤單。\n《九天玄女》",
        "落花满天蔽月光，借一杯附荐凤台上。帝女花带泪上香，愿丧生回谢爹娘。偷偷看，偷偷望，佢带泪带泪暗悲伤。我半带惊惶，怕驸马惜鸾凤配，不甘殉爱伴我临泉壤。\n《帝女花-香夭》",
        "冷冷雪蝶临梅岭，曲中弦断香销劫后城，此日红阁有谁个悼崇祯，我灯昏梦醒哭祭茶亭；钗分玉碎想殉身归幽冥，帝后遗骸谁愿领。碧血积荒径。\n《帝女花-庵遇》",
        "飘渺间往事如梦情难认。百劫重逢缘何埋旧姓，夫妻断了情，唉鸳鸯已定，烽烟已靖，我偷偷相试佢未吐真情令我惊。\n《帝女花-庵遇》",
        "贮泪已一年，封存叁百日，尽在今时放，泣诉别离情。昭仁劫后血痕鲜，可怜梦觉剩空筵。空悼落花，不见如花影，难招紫玉魂，难随黄鹤去，估不到维摩观，便是你驻香庭。避世情难长孤另，轻寒夜拥梦难成。往日翠拥诸为千人敬。今日更无一个可叮咛。\n《帝女花-相认》",
        "山残水剩痛兴亡，劫后重逢悲聚散。有梦回故苑，无泪哭余情。雨后帝花飘，我不死无以对先王，偷生更难以谢民百姓。不孝已难容，欺世更无可恕，我虽生人世上，但鬼录已登名。\n《帝女花-相认》",
        "拾釵人從絕塞歸，墜釵人已移情去，歸來空抱恨，此恨永難翻。八千里路夢遙遙，壩陵橋畔柳絲絲，恍見夢中人，招手迎郎返。驚見賣釵人，釵未斷時情已斷，未溫前夢夢先殘。胡不念花院盟香，胡不念柳驛攀條，胡不念臨歧曾共鴛鴦盞。也應念紫陌天緣，也應念紅閨美眷，更有盟心句，寫在烏絲闌。\n《紫钗记-吞钗拒婚》",
        "十郎喊句舊情淡，偷將紫釵空泣嘆，怨句負愛寡恩紅顏。夢斷香銷我痛不欲生，哭分飛釵燕不待我回還。吞釵寧玉碎。情已冷，郎決殉愛，願以死酬俗眼。\n《紫钗记-吞钗拒婚》",
        "霧月夜抱泣落紅，險些破碎了燈釵夢。喚魂句頻頻喚句，卿須記取再重逢。嘆病染芳軀不禁搖動，重似望夫山半欹帶病容。千般話猶在未語中，深驚燕好皆變空。\n《紫钗记-剑合钗圆》",
        "處處仙音飄飄送，暗驚夜臺露凍。愁共怨待向陰司控，聽風吹翠竹，昏燈照影印簾櫳。霧夜少東風，誰嗰扶飛柳絮？\n《紫钗记-剑合钗圆》",
        "聞鐘鼓，郎就鳳凰筵，橫來白羽穿心箭，酸得我芳心碎盡步顛連。女子由來心眼淺，那禁她，金枝玉葉年年月月，依戀伴郎眠？妒酸風，怒滿了桃花雙臉。\n《紫钗记-节镇宣恩》",
        "褪色桐棺露芳名，灯灭半浮纤丽影，疑是芳魂回柳舍，却缘是竹影乱花台，拜一拜棺中睡美人，避一避泉台新鬼恨，莫言咫尺是芳邻，须知阴阳如隔海。\n《再世红梅记-环佩魂归》",
        "花魁恨，一语恼孤鸾。赏灯谁断风筝线，盘秋谁断并头莲。有千金难买还魂券，秋灯灭，难以再重燃。摧花负罪你当难免。\n《蝶影红梨记-宦游三错》",
        "欲把舊調重複唱，弦驟斷難續上，碧天雲淡雨寒，天幡布陣有胭脂將，不納這癡心漢，憶倩女同墜愛網，登仙百念全喪，雙仙斜立兩旁，咫尺不願看，厭倦了塵俗相，化玄女依歸太上，塵俗客為你甘殉葬，曾付了相思賬，火劫鴛鴦，深深揖拜眾仙要念我狂。\n《九天玄女-於歸》",
        "不见风雨魂兮夜半还，红楼残梦断，黄竹血迹斑。痴情误，怎料到接木移花害了绝代颜。痛惜枉有天生宋玉才，叹未能唤魄返，欲上天阙，再落泉下探，秋雨春风梦到潇湘冷，悼翠悲红情泪染花瓣，葬花人难留俗眼，生死不了情永在世间，未许化烟消云散。\n《红楼梦-幻觉离恨天》",
        "三千风雨满仙山，隐约现情帆。爱深渐疏淡任风吹散。绾结三生一朝分散。红楼孽满，梦己断厌尘寰。剩有泪痕，付与鹃雁。魂在故园，暂寄潇湘不散。看宝玉痛孤单，惨切唤魂还，天宫泣幽咽，人间空嗟叹。往事应了，爱恨怕翻。弦断再续，也觉空泛。\n《红楼梦-幻觉离恨天》",
        "乘龙夜醉挽嫦娥拜，再续断钗，不须惊恐怕浪怕风，残庐结婚越觉情浓。龙凤有烛，奴实有福，秋波偷偷送。虽四壁皆空，夫妻居于破巢亦胜深宫。\n《跨凤乘龙-鸾凤同巢》",
        "十万横磨剑，宛似在目前，万众投鞭流可断，总不枉圣上归为臣虏强图存。当日申包胥，也遂存楚愿，深冀江南俊彦胜前贤。复国心急如弦上箭，壮怀激烈扣心弦。江南锦绣好江山，难容黩武穷兵占。\n《李后主-去国归降》",
        "花逐雨中飘，曲随广陵散。感时知有恨，惜别悄无言。一身能负几重忧，人间没处可安排，念往事合应肠断。冷雨送斜阳，问几许兴亡恨，怕从野叟话桑田。如此好江山，别时容易见时难，回首依依无限怨。\n《李后主-去国归降》",
        "烟波江上使人愁，眼前尽是遗民泪，怕牵衣泣血问归旋。江山依然，痛皇业变迁。此去囚居宋土，难卜再复旋。不知哪一天，不知哪一天，复我山川。相看倍心酸，难禁丝丝血泪垂 偷眼望宋船 撩乱了方寸。广陵台殿已荒凉 吴苑宫帏今冷落 剩一程风雨送愁人 叹千里江山寒色远。\n《李后主-去国归降》"
    )

    private fun setupStatusBar() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = resources.getColor(R.color.colorPrimaryDark)
    }
}