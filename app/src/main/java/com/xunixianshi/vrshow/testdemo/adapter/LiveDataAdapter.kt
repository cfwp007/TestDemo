package com.xunixianshi.vrshow.testdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xunixianshi.vrshow.testdemo.R
import com.xunixianshi.vrshow.testdemo.obj.PersonLive

/**
 * @ClassName:      LiveDataAdapter$
 * @Description:     java类作用描述
 * @Author:         wpeng
 * @CreateDate:     2021/5/18$ 15:06$
 * @Version:        1.0
 */
class LiveDataAdapter :BaseQuickAdapter<PersonLive,BaseViewHolder>(R.layout.livedata_layout_item) {

    override fun convert(holder: BaseViewHolder, item: PersonLive) {
        holder.setText(R.id.name,item.name)
        holder.setText(R.id.address,item.address)

    }
}