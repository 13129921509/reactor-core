package com.cai.reactor.core

import com.cai.reactor.config.ReactorException
import org.springframework.stereotype.Component

@Component
class GroupMatchRule extends AbstractRule{

    final static SINGLE_GROUP_KEY = "*"

    final static DYNAMIC_GROUP_KEY = "?"

    Map<Class, List> groups

    GroupMatchRule() {
        this.groups = [:]
    }

    @Override
    List<Consumer> match(Event e) {
        List<Consumer> cs = []
        List keys = e.topic().split("\\.").toList()
        if (keys.contains("?")){
            groups.each {k,v ->
                Consumer consumer
                if (matchDynamicKey(v, keys) && (consumer = findConsumer(k)) != null){
                    cs.add(consumer)
                    consumer = null
                }
            }
            return cs
        }
//        simple mode
        groups.each {k,v ->
            Consumer consumer
            if (groupMatch(v, keys) && (consumer = findConsumer(k)) != null){
                cs.add(consumer)
                consumer = null
            }
        }
        return cs
    }

    static boolean matchDynamicKey(List<String> keys, List<String> topics){
        Node e = createNode(keys)
        Node t = createNode(topics)
        Node m = e
//        if (e.composeLen(t))
//            m=e
//        else
//            m=t
        while (m && t){
            switch (t.key){
                case DYNAMIC_GROUP_KEY:
                    if (t.hasNext){
                        if (t.next.key == SINGLE_GROUP_KEY)
                            throw new ReactorException("mode a.?.*.b not allowed")
                        m = m.matchLastKey(t.next.key)
                        if (!m)
                            return false
                        t = t.next
                    } else {
                        return true
                    }
                    break
                case SINGLE_GROUP_KEY:
                    t = t.next
                    m = m.next
                    break
                default:
                    if (t.key != m.key)
                        return false
                    t = t.next
                    m = m.next
            }
        }
        if (m || t)
            return false
        return true
    }

    public static void main(String[] args) {
        // * 匹配 1个，? 匹配多个a
        println matchDynamicKey(["a","b","b","d","d","c"],["a","*","b","?","c"]) //true
        println matchDynamicKey(["a","b","b","d","d","c"],["*","*","b","?","c"]) //true
        println matchDynamicKey(["a","b","b","d","d","c"],["a","c","b","?","c"]) //false
        println matchDynamicKey(["a","b","b","d","d","c"],["a","*","b","?"])     // true
        println matchDynamicKey(["a","b","b","d","d","c"],["a","?","b","?","c"]) //true
        println matchDynamicKey(["a","b","b","d","d","c"],["a","*","?"]) //true
        println matchDynamicKey(["a","b","b","d","d","c"],["a","?","*"]) //throw exception

    }

    static Node createNode(List<String> keys){
        Node node = new Node()
        Node tail = node
        Iterator<String> iterator = keys.iterator()
        while (iterator.hasNext()){
            String key = iterator.next()
            node.key = key
            if (iterator.hasNext()){
                node.hasNext = true
                node.next = new Node()
                node = node.next
            } else{
                node.hasNext = false
            }
        }
        return tail
    }

    @Override
    void afterSubscribe(Consumer consumer) {
        groups.put(consumer.class, consumer.key().split("\\.").toList())
    }

    static boolean groupMatch(List<String> keys, List<String> topics){
        if (keys.size() != topics.size())
            return false
        for (int i = 0 ; i < keys.size() ; i++){
            if (!(keys[i] == topics[i] || SINGLE_GROUP_KEY == topics[i])){
                return false
            }
        }
        return true
    }

    Consumer findConsumer(Class<Consumer> clazz){
        return consumers.find {
            it.class == clazz
        }
    }
}


class Node {

    String key

    Node next

    boolean hasNext

    Node matchLastKey(Node node, String key){
        Node lastNode = null
        while (node.hasNext){
            if (node.key == key){
                lastNode = node
            }
        }
        return lastNode
    }

    Node matchLastKey(String key){
        Node node = this
        Node lastNode = null
        while (node){
            if (node.key == key){
                lastNode = node
            }
            node = node.next
        }
        return lastNode
    }

    boolean composeLen(Node n){
        Node t = this
        while (t.hasNext && n != null){
            t = t.next
            n = n.next
        }
        if (t.hasNext){
            return true
        }
        return false
    }
}