package com.cas;

import ch.qos.logback.classic.Logger;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 解决aba的另一个方法
 * 我们只关心有没有被改变  不关心改变了几次
 */

@Slf4j(topic = "c.FoaABA")
public class FoaABA {
    public static void main(String[] args) {
        GarbageBag bag = new GarbageBag("已经装满了垃圾");
        // true表示垃圾已满
        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag,true);
        log.debug("start....");
        // 1.main线程获取到ref的对象 这个时候标记是true
        GarbageBag old = ref.getReference();
        log.debug("{}",old.toString());
        clean(ref);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("想更换一个袋子");
        // main线程再次比对：此时ref的标记已经是false，main线程那true和false就行对比，导致对比失败 新的对象就没有new出来
        boolean success = ref.compareAndSet(old, new GarbageBag("新的袋子"), true, false);
        log.debug("更换成功了么?:{}",success);
        log.debug(ref.getReference().toString());


    }
    // 另一个线程来改变了标记
    public static void clean(AtomicMarkableReference<GarbageBag> reference){
        GarbageBag bag = reference.getReference();
        // 2.另一个线程获取了ref里的对象 并且把标记改为了false
        new Thread(()->{
            if (bag.getDesc().equals("已经装满了垃圾")){
                log.debug("倒掉袋子里的垃圾");
                bag.setDesc("垃圾已经被保洁员倒掉，现在是空的");
                reference.compareAndSet(bag,bag,true,false);
            }
        }).start();
    }
}


class GarbageBag{
    private String desc;
    public GarbageBag(String desc){
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return super.toString() + " " + desc;
    }
}
