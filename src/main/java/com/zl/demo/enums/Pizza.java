package com.zl.demo.enums;

import com.itextpdf.text.pdf.PRIndirectReference;

/**
 * @Author zhuanglei
 * @Date 2021/3/24 8:30 下午
 * @Version 1.0
 */
public class Pizza {
    private PizzaStatus status;
    private enum PizzaStatus{
        OEDERED(5){
            @Override
            public boolean isOrdered() {
                return true;
            }
        },
        READY(2){
            @Override
            public boolean isReady() {
                return true;
            }
        },
        DELIVERY(0){
            @Override
            public boolean isDelivery() {
                return true;
            }
        }
        ;
        private int timeToDelivery;
        public boolean isOrdered(){return false;}
        public boolean isReady(){return false;};
        public boolean isDelivery(){return false;};



        PizzaStatus(int timeToDelivery){
            this.timeToDelivery = timeToDelivery;
        }

        public int getTimeToDelivery() {
            return this.timeToDelivery;
        }
    }
    public boolean isDeliverable() {
        return this.status.isReady();
    }

    public void printTimeToDeliver() {
        System.out.println("Time to delivery is " +
                this.getStatus().getTimeToDelivery());
    }

    public PizzaStatus getStatus() {
        return status;
    }

    public void setStatus(PizzaStatus status) {
        this.status = status;
    }

    public static void main(String[] args) {
        Pizza pizza = new Pizza();
        pizza.setStatus(PizzaStatus.READY);
        System.out.println(pizza.isDeliverable());
    }
}
