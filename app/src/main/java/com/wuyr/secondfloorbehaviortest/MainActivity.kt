package com.wuyr.secondfloorbehaviortest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.*
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import com.wuyr.secondfloorbehavior.SecondFloorBehavior
import kotlinx.android.synthetic.main.act_main.*
import kotlinx.android.synthetic.main.layout_menu.*


/**
 * @author wuyr
 * @github https://github.com/wuyr/SecondFloorBehavior
 * @since 2019-09-29 下午12:02
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        setupStatusBar()
        init()
    }

    private lateinit var adapter: TextListAdapter
    private lateinit var behavior: SecondFloorBehavior

    private fun init() {
        initRecyclerView()
        initBehavior()
        initRadioGroup()
        initSeekBar()

        firstFloorView.openDrawer(GravityCompat.START)
    }

    fun onEnterSecondFloor() {
        progressBar.animate().alpha(0F).setDuration(behavior.enterDuration * 2).start()
    }

    fun onExitSecondFloor() {
        progressBar.animate().alpha(1F).setDuration(behavior.exitDuration * 2).start()
    }

    private fun initBehavior() {
        //获取到Behavior实例
        behavior = (secondFloorView.layoutParams as
                CoordinatorLayout.LayoutParams).behavior as SecondFloorBehavior
        //监听状态
        behavior.setOnStateChangeListener {
            state.text = when (it) {
                SecondFloorBehavior.STATE_NORMAL -> getString(R.string.idle)
                SecondFloorBehavior.STATE_DRAGGING -> getString(R.string.dragging)
                SecondFloorBehavior.STATE_PREPARED -> getString(R.string.prepared)
                SecondFloorBehavior.STATE_OPENING -> getString(R.string.opening)
                SecondFloorBehavior.STATE_OPENED -> getString(R.string.opened)
                SecondFloorBehavior.STATE_CLOSING -> getString(R.string.closing)
                else -> ""
            }
        }
        //如果正在刷新，就不允许进入二楼
        behavior.setOnBeforeEnterSecondFloorListener { !refreshLayout.isRefreshing }
    }

    @SuppressLint("NewApi")
    private fun initRadioGroup() {
        interpolator.setOnCheckedChangeListener { _, checkedId ->
            (when (checkedId) {
                R.id.accelerateDecelerate -> AccelerateInterpolator()
                R.id.decelerate -> DecelerateInterpolator()
                R.id.bounce -> BounceInterpolator()
                R.id.overshoot -> OvershootInterpolator()
                else -> LinearInterpolator()
            }).apply {
                behavior.setEnterAnimationInterpolator(this)
                behavior.setExitAnimationInterpolator(this)
                behavior.setExitAnimationInterpolator(this)
            }
        }
    }

    private fun initSeekBar() {
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                when (seekBar.id) {
                    R.id.enterDuration -> {
                        behavior.enterDuration = progress.toLong()
                        enterDurationText.text = progress.toString()
                    }
                    R.id.exitDuration -> {
                        behavior.exitDuration = progress.toLong()
                        exitDurationText.text = progress.toString()
                    }
                    R.id.dampingRatio -> {
                        (progress / 100F).let { if (it == 0F) .01F else it }.run {
                            behavior.dampingRatio = this
                            dampingRatioText.text = toString()
                        }
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        }.run {
            enterDuration.setOnSeekBarChangeListener(this)
            exitDuration.setOnSeekBarChangeListener(this)
            dampingRatio.setOnSeekBarChangeListener(this)
        }
    }

    private fun initRecyclerView() {
        secondFloorRecyclerView.adapter = ImageListAdapter(this)
        adapter = TextListAdapter(this, data.toMutableList())
        firstFloorRecyclerView.adapter = adapter
        refreshLayout.setOnRefreshListener {
            refreshLayout.postDelayed({
                refreshLayout.isRefreshing = false
                adapter.setData(*data)
            }, 2000)
        }
    }

    override fun onBackPressed() {
        if (firstFloorView.isDrawerOpen(GravityCompat.START)) {
            firstFloorView.closeDrawer(GravityCompat.START)
        } else {
            if (behavior.state == SecondFloorBehavior.STATE_OPENED) {
                behavior.leaveSecondFloor()
            } else {
                super.onBackPressed()
            }
        }
    }

    private var next = false
    private val data: Array<String>
        get() =
            if (next) {
                next = false
                arrayOf(
                    "（裴禹不由自主的冲口而叫白）姑娘...姑娘...（口古）姑娘，残桥在上，画船在下，恨芍药为烟雾所笼，帐芙蓉为垂杨所隔，能否请仙子降下云阶，待凡人默志芳容，归去焚香供奉。",
                    "（慧娘口古）秀才郎，明月在天，青莲在地，既知明月高不可攀，何必潜落江心而思抱月呢，不若请秀才早归云馆，努力攻书，屏息万念，搏一点簪花状元红。（欲下介）",
                    "（裴禹情急白）姑娘...（随意将琴放于垂杨下石趸之上，即向船而拜口古）姑娘，所谓缘份在天，邂逅由人，既拨柳而情愫暗通，何必吝啬芳容，陷我沉沦于绮梦呢。姑娘请下船...姑娘请下船...",
                    "（慧娘口古）呆秀才，所谓祸福由天，拾取由人，请辨画船旗上帜，你都莫向佳人枉鞠躬。（裴禹不顾一切亦不视旗呼叫白）姑娘...姑娘你慢行...姑娘你慢行...",
                    "（长二王下句）茫茫烟水觅芳踪，浅笑轻颦常入梦，烟疑粉坠，水像脂融，似叶扁舟幸得风吹送，喜见锦帆斜挂夕阳中，我是彩云卿是凤，今日天台有路，莫教雾蔽烟笼。虽是萍水相交，何忍化秋云春梦。（白）姑娘你若不下船，小生嘛...就长拜不止。（对船不停膜拜介）",
                    "（慧娘觊状笑介欲行将回船又止步回望裴禹滚花下句）唉，镜花水月原是幻。（裴禹叫白）姑娘...姑娘... （慧娘意又不忍介续唱）但任教铁石为心也动容。（上岸）（裴禹见慧娘下船心中大喜忙躬身再下礼白）姑娘，小生有礼。 （慧娘又惊又喜介闪入柳阴下滚花上句）轻锁意马系心猿，借嫩柳深藏情万种。",
                    "（裴禹唱蕉窗夜雨）惊艳女，含颦愁对春风，露半面挽玉带低弄娇羞态欲藏嫩柳中，似烟罩芙蓉，腮有泪溅玉容。（白）哎呀...常言仙子无愁，凡人有恨，问何以蓦地相逢，姑娘你...你腮边有泪... ",
                    "（慧娘并不回答，急于回身拭泪）（裴禹续白）唉，嗟莫是柳外桃花逢雨劫，飘零落向画船中？",
                    "（慧娘接唱）有个书生得解我悲痛，拂柳相对无语情半通，诗内寄意是怜爱还是暗讽，花飘泊万古也类同。（白）唉桃花雨劫是千古不移之例，飘向画船是因风不由自主，秀才渴欲一见，妾都不忍再敞其色，有违雅意。如此君愿巳偿，妾心都亦了，秀才你何不请回呢？",
                    "（裴禹急白）姑娘，我抱琴而来，万不能空手而归架。（接唱）见否有绿琴物微缘份重。（慧娘接唱）恕此柳外人见琴难递送。（白）秀才，你既知琴在柳荫，又何必见而故问呢。",
                    "（裴禹接唱）你既知我是醉翁，几翻欲语还自控，花到怒放谁户种，抑或闰女未嫁盼望有奇逢。（慧娘白）秀才，你何必明知故问呢？（裴禹白）下，我何曾知道呀。（慧娘接白）你既然有柳外桃花逢雨劫，飘零落向画船中之句，又点会唔知我既身世呢。",
                    "（慧娘接唱）花劫断客梦，巳骤逢暴雨罡风，暗将诗句诵，湘女更动容，莫个叩芳踪，恼煞权臣太凶，负你惜花义勇。（裴禹接唱）愁闻弦断曲终，恨见面巳断碎春梦，若七仙女召回入太空，剩下追舟者被风揭断蓬。",
                    "（慧娘接唱）你有意栽花潇洒更英勇，惜那花沾泥絮尘半封。（白）既卖之心，难以再为君赠啰。（裴禹接唱）唉，北地作客负才气寻觅爱宠，得见知音侣巳卖身困玉笼。（白）不朽之情，哀哀伏求卿鉴。",
                    "（慧娘接）辜负伯牙琴。（裴禹接）泪巳难自控。（慧娘接）知音再复寻。（裴禹接）俗世才未众。",
                    "（慧娘接唱）你看花在镜中，相思自惹遗恨痛，一切为我会能断送，休要慕我算是饶侬。（一路讲一路抱琴还与裴禹悲咽白）谢秀才情深，恕红颜薄命，一面之缘，请从此休，带泪还琴，请从此别，秀才请回，言尽于此矣。",
                    "（裴禹接）相见非似梦，奈何别也空匆，叹丹山有凤，此后咫尺隔万重。（慧娘接）怯酸风，易惹无情剑锋，挥巾目送。",
                    "（裴禹失望而行至石桥不禁几回眺望介滚花下句）唉，相如枉奏求凰调，岂料知音恨晚逢。伤心怒碎伯牙琴，（碎琴介）此后琴碎情亡只是留恨痛。（快快而行下介）"
                )
            } else {
                next = true
                arrayOf(
                    "",
                    "（排子头一句作上句起幕）（昭仁宫主什边柳荫上介台口滚花下句）凤彩门前灯千盏，扫尽深宫半月愁。愁云战雾罩南天，偏是凤台设下求凰酒。",
                    "（白榄）前年父王喻礼部，替王姐长平择配偶。祇求身出官宦家，年华双十人俊秀。凤台千尺谁能攀，凤台枉设葡萄酒。有个周世显，才锦绣。礼部选之应凤徵，今夕凤台新试酒。环佩声传凤来仪，等闲谁敢轻咳嗽。",
                    "（长平宫主小曲醉酒唱上）红牙低声奏，冷香侵凤楼，甘自寂寞看韶华溜。空对月夜岁老烧金兽，更添一段愁。求凰宴，莫设凤台难从浊里求。若是无缘怎生将就。",
                    "（昭仁白）拜见王姐。（长平微笑白）昭仁二妹，我地姐妹之间应叙伦常，少行宫礼叻。（昭仁天真介口古）王姐，礼部选来一个你唔岩，两个又唔岩，王姐你独赏孤芳，恐怕终难寻偶。",
                    "（长平笑介口古）唉二妹，我本无求偶之心，怎奈父王佢催粧有意，我话其实都系多馀既唧，正是千军容易得，一婿最难求。（半羞介白）内侍臣，与哀家传。（侍臣台口传旨介白）遵旨。呔，宫主有命，周世显朝见。",
                    "（周锺伴周世显宫门打引上介）（世显台口诗白）孔雀灯开五凤楼。轻袍暖帽锦貂裘。敏捷当如曹子健，潇洒当如秦少游。（欲入介）（周锺一手拖世显另场白）喂，咪行住...咪行住...",
                    "（滚花下句）喂...帝女花都不比宫墙柳，长平慧质殊少有，君王有事必与帝女谋，你叁生有幸得向裙前叩，切记凤台应对莫轻浮。难得云英今夕会裴航，你要一遍虔诚求柱扣。",
                    "（世显一才窥望惊艳介白）哦，好一朵（秃头长二王下句）芙蓉出水百花羞，眉似苏堤春晓柳，盈盈秋水，为何轻罩雾烟愁。嗟莫是坐月怨秦箫，抑或是倚风寒翠袖。（上前跪介白）臣太仆左都尉之子周世显叩见宫主，愿宫主千千岁。",
                    "（长平望也不望冷然口古）平身。（介）周世显，语云男儿膝下有黄金，你奈何折腰求凤侣，敢问士有百行，以何为首。",
                    "（世显口古）宫主，所谓新入宫庭，当行宫礼，宫主是天下女子仪范，奈何出一语把天下男儿污辱，敢问女有四德，到底以边一样占先头呀？",
                    "（长平重一才慢的的震怒依然不望冷笑口古）周世显，擅辞令者，都祇合游说于列国，倘若以辞令求偶于凤台，未见其诚，益增其丑咋。",
                    "（世显绝不相让介口古）宫主，言语发自心声，辞令寄于学问，我虽无经天纬地之才，却有怜香惜玉意，可惜人不以真诚待我，我又何必以诚信相投呢。（周锺在旁怨世显傲慢介）",
                    "（长平重一才慢的的回头见世显，惊其才貌，徐徐回笑白）哎呀...哦...酒来...（慢板唱下介）侍臣递过紫金瓯，翠盆香冷霓裳奏，借一杯琼浆玉液，谢适才语出轻浮。",
                    "（世显接杯秃头中板）谢宫主玉手赐琼浆，好比月破蓬莱，云迷楚岫。甘露未为奇，玉杯宁足罕，最难得是眉目暗相投。彩凤有翠拥红遮，经已兰麝微闻，我未接葡萄，香先透。翘首望瑶池，天上有金童玉女，人间亦有凤侣莺俦。（滚花）忽见含樟树，倚殿千年，怎得情如合抱仝长寿。",
                    "（周锺白）宫主。（二王下句）正是瑶池无俗客，凤台只配凤凰游。（序）老臣所荐可合心头。（曲）望宫主赐下一言，好待向君皇回奏。（序）未知佢雀屏能中否？"
                )
            }

    private fun setupStatusBar() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = resources.getColor(R.color.colorPrimaryDark)
    }
}