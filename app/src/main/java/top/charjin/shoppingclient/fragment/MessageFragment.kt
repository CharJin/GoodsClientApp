package top.charjin.shoppingclient.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.message_fragment_main.*
import top.charjin.shoppingclient.R
import top.charjin.shoppingclient.adapter.MessageAdapter
import top.charjin.shoppingclient.model.MessageModel
import java.util.*

class MessageFragment : BaseFragment() {
    private lateinit var viewRoot: View

    private lateinit var messageAdapter: MessageAdapter
    private val data = ArrayList<MessageModel>()


    override fun getLayoutId(): Int {
        return R.layout.message_fragment_main
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewRoot = LayoutInflater.from(context).inflate(R.layout.message_fragment_main, container, false)
        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView
        messageAdapter = MessageAdapter(data)
        rv_message.layoutManager = LinearLayoutManager(context)
        rv_message.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv_message.adapter = messageAdapter

        // SwipeRefreshLayout
        swipe_layout_message.setColorSchemeColors(resources.getColor(R.color.app_swipe_circle_color))
        swipe_layout_message.setOnRefreshListener(this::refreshMsg)

    }

    override fun onResume() {
        super.onResume()
        initMessageList()
    }

    private fun initMessageList() = Thread {
        repeat(100) { data.add(MessageModel("", "这是标题$it", "[最新消息]")) }
        activity.runOnUiThread { messageAdapter.notifyDataSetChanged() }
    }.start()


    /**
     * 刷新消息页面
     */
    private fun refreshMsg() = Thread {
        try {
            Thread.sleep(1500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        swipe_layout_message.isRefreshing = false
    }.start()
}