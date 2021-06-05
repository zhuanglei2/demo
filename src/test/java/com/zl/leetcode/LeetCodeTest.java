package com.zl.leetcode;

import com.alibaba.fastjson.JSON;
import com.zl.demo.ArrayCombination;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author zhuanglei
 * @Date 2021/5/25 7:26 下午
 * @Version 1.0
 */
@Slf4j
@SpringBootTest(classes = ArrayCombination.class)
public class LeetCodeTest {
    @Test
    public void reserveListTest(){
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        reverseList(node1);
    }

    public void soutNode(ListNode node1){
        while (node1!=null){
            System.out.println(node1.val);
            node1 = node1.next;
        }
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 调用递推公式反转当前结点之后的所有节点
        // 返回的结果是反转后的链表的头结点
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
