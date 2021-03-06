package com.csw.hashtab;

import java.util.Scanner;

/**
 * @Auther: 行路
 * @Date: Created on 2020/4/22 20:21 星期三
 * @Description: com.csw.hashtab 哈希表
 * @version: 1.0
 */
public class HashTabDemo {
    public static void main(String[] args) {
        //创建哈希表
        HashTab hashTab=new HashTab(7);
        //写一个简单的菜单
        String key="";
        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println("add:添加雇员");
            System.out.println("list:显示雇员");
            System.out.println("find:查找雇员");
            System.out.println("exit:退出系统");
            key=scanner.next();
            switch (key){
                case "add":
                    System.out.println("输入id");
                    int id=scanner.nextInt();
                    System.out.println("输入名字");
                    String name=scanner.next();
                    //创建雇员
                    Emp emp=new Emp(id,name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("请输入要查找的id");
                    id=scanner.nextInt();
                    hashTab.findEmpById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    break;
            }
        }
    }

}
//创建HashTab,管理多条链表
class HashTab{
    private EmpLinkedList[] empLinkedListArray;
    private int size; //表示共有多少条

    //构造器
    public HashTab(int size){
        this.size=size;
        //初始化empLinkedListArray
        empLinkedListArray=new EmpLinkedList[size];
        //不能忘,分别初始化每个链表
        for(int i=0;i<size;i++){
            empLinkedListArray[i]=new EmpLinkedList();
        }
    }

    //添加雇员
    public void add(Emp emp){
        //根据员工的id,得到该员工应当添加到那条链表
        int empLinkedListNo=hashFun(emp.id);
        //将emp添加到链表中
        empLinkedListArray[empLinkedListNo].add(emp);

    }
    //遍历所有表,哈希表hashtab
    public void list(){
        for (int i=0;i<size;i++){
            empLinkedListArray[i].list(i);
        }
    }

    //编写散列函数,使用一个简单取模法
    public int hashFun(int id){
        return id%size;
    }

    //根据输入的Id,查找这个雇员
    public void findEmpById(int id){
        //使用散列函数确定到那条链表
        int empLinkedListNo=hashFun(id);
        Emp emp=empLinkedListArray[empLinkedListNo].findEmpById(id);
        if(emp!=null){
            //找到
            System.out.printf("在第%d条链表中找到雇员id=%d\n",(empLinkedListNo+1),id);
        }else{
            System.out.println("在哈希表中没有找到该雇员~");
        }
    }

}



/**
 * 表示一个雇员
 */
class Emp{
    public int id;
    public String name;
    public Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
//创建EmpLinkedList,表示链表
class EmpLinkedList{
    //头指针,执行第一个Emp,因此我们这个链表的head是直接指向第一个Emp
    private Emp head;  //默认null

    //添加雇员到链表
    //1.假定,当添加雇员时 ,id是自增的,即id的分配总是从小到大
    //因此我们将该雇员直接加入到本链表即可
    public void add(Emp emp){
        //如果添加的是第一个雇员
        if(head==null){
            head=emp;
            return ;
        }
        //如果不是第一个雇员,则使用辅助指针,帮助定位到最后
        Emp curEmp=head;
        while (true) {
            if (curEmp.next == null) {
                //说明已经到最后了
                break;
            }
            curEmp = curEmp.next; //后移
        }
        //当退出时直接将 emp加入链表
        curEmp.next=emp;
    }

    //遍历链表的雇员信息
    public void list(int no){
        if(head==null){
            //说明链表为空
            System.out.println("第"+(no+1)+"条链表为空");
            return;
        }
        System.out.print("第"+(no+1)+"条链表的信息为:");
        Emp curEmp=head;
        while (true){
            System.out.printf("->id=%d name=%s\t",curEmp.id,curEmp.name);
            if(curEmp.next==null){
                //说明curEmp已经是最后节点了
                break;
            }
            curEmp=curEmp.next;  //后移遍历
        }
        System.out.println();
    }

    //根据id查找雇员
    //如果查找到,就返回Emp,如果没有找到,就返回null
    public Emp findEmpById(int id){
        //判断链表是否为空
        if(head==null){
            System.out.println("链表未找到");
            return null;
        }
        //辅助指针
        Emp curEmp=head;
        while (true){
            if(curEmp.id==id){
                //找到,这时curEMp就指向要查找的雇员
                break;
            }
            //退出
            if(curEmp.next==null){
                //说明遍历当前链表没有找到雇员
                curEmp=null;
                break;
            }
            curEmp=curEmp.next;
        }
        return curEmp;
    }
}


