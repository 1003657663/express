package com.expressba.expressuser.myelement;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.expressba.expressuser.R;
import com.expressba.expressuser.main.UIFragment;
import com.expressba.expressuser.map.MyBaiduMapFragment;

/**
 * Created by songchao on 16/5/11.
 */
public class MyFragmentManager {
    /**
     * 用于fragment之间进行切换,默认不pop直接切换，会被添加进返回栈中
     * @param fromFragmentClass
     * @param toFragmentClass
     * @param args
     * @param fm
     */
    public static void turnFragment(Class<? extends UIFragment> fromFragmentClass, Class<? extends UIFragment> toFragmentClass, Bundle args, FragmentManager fm){

        FragmentTransaction ft = fm.beginTransaction();
        //被切换的fragment标签
        String fromTag = null;
        if(fromFragmentClass!=null) {
            fromTag = fromFragmentClass.getName();
        }
        //切换到fragment标签
        String toTag = toFragmentClass.getName();

        UIFragment fromFragment = (UIFragment) fm.findFragmentByTag(fromTag);
        UIFragment toFragment = (UIFragment) fm.findFragmentByTag(toTag);
        //如果要切换的fragment不存在，那么创建
        if(toFragment == null){
            try {
                toFragment = toFragmentClass.newInstance();
                toFragment.setArguments(args);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else {
            if (args != null) {
                //toFragment.getArguments().putAll(args);
                toFragment.setBundle(args);
            }
        }
        //设置fragment切换效果
        ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_left,R.animator.slide_in_right,R.animator.slide_out_right);
        //如果tofragment已经添加到栈里面，那么隐藏现在的fragment显示要切换的fragment，
        //如果没有添加，那么隐藏现在的，添加要切换的fragment
        if(toFragment!=null) {
            if(fromFragment!=null) {
                if (!toFragment.isAdded()) {
                    ft.hide(fromFragment).add(R.id.fragment_container_layout, toFragment, toTag);
                } else {
                    ft.hide(fromFragment).show(toFragment);
                }
            }else{
                if(!toFragment.isAdded()){
                    ft.add(R.id.fragment_container_layout,toFragment,toTag);
                }else{
                    ft.show(toFragment);
                }
            }
            //添加到回退栈
            ft.addToBackStack(fromTag);
            //不保留状态提交事务
            ft.commitAllowingStateLoss();
        }
    }


    /**
     * 用于百度fragment之间进行切换,默认不pop直接切换，会被添加进返回栈中
     * @param fromFragmentClass
     * @param toFragmentClass
     * @param args
     * @param fm
     */
    public static void turnBaiduFragment(Class<? extends UIFragment> fromFragmentClass, Class<MyBaiduMapFragment> toFragmentClass, Bundle args, String expressID, FragmentManager fm){

        FragmentTransaction ft = fm.beginTransaction();
        //被切换的fragment标签
        String fromTag = null;
        if(fromFragmentClass!=null) {
            fromTag = fromFragmentClass.getName();
        }
        //切换到fragment标签
        String toTag = toFragmentClass.getName();

        UIFragment fromFragment = (UIFragment) fm.findFragmentByTag(fromTag);
        MyBaiduMapFragment toFragment = (MyBaiduMapFragment) fm.findFragmentByTag(toTag);
        //如果要切换的fragment不存在，那么创建
        if(toFragment == null){
            try {
                toFragment = toFragmentClass.newInstance();
                toFragment = toFragment.newInstance(args,expressID);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else {
            if (args != null) {
                //toFragment.getArguments().putAll(args);
                toFragment.setBundle(args,expressID);
            }
        }
        //设置fragment切换效果
        ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_left,R.animator.slide_in_right,R.animator.slide_out_right);
        //如果tofragment已经添加到栈里面，那么隐藏现在的fragment显示要切换的fragment，
        //如果没有添加，那么隐藏现在的，添加要切换的fragment
        if(toFragment!=null) {
            if(fromFragment!=null) {
                if (!toFragment.isAdded()) {
                    ft.hide(fromFragment).add(R.id.fragment_container_layout, toFragment, toTag);
                } else {
                    ft.hide(fromFragment).show(toFragment);
                }
            }else{
                if(!toFragment.isAdded()){
                    ft.add(R.id.fragment_container_layout,toFragment,toTag);
                }else{
                    ft.show(toFragment);
                }
            }
            //添加到回退栈
            ft.addToBackStack(fromTag);
            //不保留状态提交事务
            ft.commitAllowingStateLoss();
        }
    }


    /**
     * 用于fragment之间进行切换,可以选择是否添加到回退栈。
     * 在什么情况下不需要添加回退栈？
     * 1. 刚进入app添加MainFragment，防止按返回键出现空白
     * 2. 在下一个fragment中按下返回键想要直接返回上一个fragment，不经过这个fragment
     * @param fromFragmentClass
     * @param toFragmentClass
     * @param args
     * @param fm
     */
    public static void turnFragment(Class<? extends UIFragment> fromFragmentClass, Class<? extends UIFragment> toFragmentClass, Bundle args, FragmentManager fm ,boolean addBackStack){

        FragmentTransaction ft = fm.beginTransaction();
        //被切换的fragment标签
        String fromTag = null;
        if(fromFragmentClass!=null) {
            fromTag = fromFragmentClass.getName();
        }
        //切换到fragment标签
        String toTag = toFragmentClass.getName();

        UIFragment fromFragment = (UIFragment) fm.findFragmentByTag(fromTag);
        UIFragment toFragment = (UIFragment) fm.findFragmentByTag(toTag);
        //如果要切换的fragment不存在，那么创建
        if(toFragment == null){
            try {
                toFragment = toFragmentClass.newInstance();
                toFragment.setArguments(args);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else {
            if (args != null) {
                //toFragment.getArguments().putAll(args);
                toFragment.setBundle(args);
            }
        }
        //设置fragment切换效果
        ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_left,R.animator.slide_in_right,R.animator.slide_out_right);
        //如果tofragment已经添加到栈里面，那么隐藏现在的fragment显示要切换的fragment，
        //如果没有添加，那么隐藏现在的，添加要切换的fragment
        if(toFragment!=null) {
            if(fromFragment!=null) {
                if (!toFragment.isAdded()) {
                    ft.hide(fromFragment).add(R.id.fragment_container_layout, toFragment, toTag);
                } else {
                    ft.hide(fromFragment).show(toFragment);
                }
            }else{
                if(!toFragment.isAdded()){
                    ft.add(R.id.fragment_container_layout,toFragment,toTag);
                }else{
                    ft.show(toFragment);
                }
            }
            if(addBackStack) {
                //添加到回退栈
                ft.addToBackStack(fromTag);
            }
            //不保留状态提交事务
            ft.commitAllowingStateLoss();
        }
    }


    /**
     * 用于fragment之间进行切换,可以选择是否添加到回退栈。
     * 在什么情况下不需要添加回退栈？
     * 1. 刚进入app添加MainFragment，防止按返回键出现空白
     * 2. 在下一个fragment中按下返回键想要直接返回上一个fragment，不经过这个fragment
     *
     * replace方法，每次进入这个页面都会重新加载页面内容
     * @param fromFragmentClass
     * @param toFragmentClass
     * @param args
     * @param fm
     */
    public static void turnReplaceFragment(Class<? extends UIFragment> fromFragmentClass, Class<? extends UIFragment> toFragmentClass, Bundle args, FragmentManager fm ,boolean addBackStack){

        FragmentTransaction ft = fm.beginTransaction();
        //被切换的fragment标签
        String fromTag = null;
        if(fromFragmentClass!=null) {
            fromTag = fromFragmentClass.getName();
        }
        //切换到fragment标签
        String toTag = toFragmentClass.getName();

        //UIFragment fromFragment = (UIFragment) fm.findFragmentByTag(fromTag);
        UIFragment toFragment = null;// = (UIFragment) fm.findFragmentByTag(toTag);
        UIFragment fromFragment = null;
        try {
            toFragment = toFragmentClass.newInstance();
            toFragment.setArguments(args);
            fromFragment = (UIFragment) fm.findFragmentByTag(fromTag);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //设置fragment切换效果
        ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_left,R.animator.slide_in_right,R.animator.slide_out_right);
        //如果tofragment已经添加到栈里面，那么隐藏现在的fragment显示要切换的fragment，
        //如果没有添加，那么隐藏现在的，添加要切换的fragment
        if(toFragment!=null) {
            if(fromFragment!=null) {
                ft.hide(fromFragment).add(R.id.fragment_container_layout, toFragment, toTag);
            }else {
                ft.add(R.id.fragment_container_layout, toFragment, toTag);
            }
            if(addBackStack) {
                //添加到回退栈
                ft.addToBackStack(fromTag);
            }
            //不保留状态提交事务
            ft.commitAllowingStateLoss();
        }
    }


    /**
     * 用于fragment之间进行切换,pop出栈传值,如果不传值那么toFragmentClass可以为空
     * 传入fromFragmentClass作用，在pop之前先隐藏fromFragment，防止出现双层fragment覆盖错误
     * @param toFragmentClass
     * @param args
     * @param fm
     */


    public static void popFragment(Class<? extends UIFragment> fromFragmentClass,Class<? extends UIFragment> toFragmentClass,Bundle args,FragmentManager fm){
        FragmentTransaction ft = fm.beginTransaction();
        //设置fragment切换效果
        ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_left,R.animator.slide_in_right,R.animator.slide_out_right);
        //如果没有参数传递，那么不需要穿件toFragment
        //如果要切换的fragment不存在，那么创建
        if(args!=null) {
            //切换到fragment标签
            String toTag = toFragmentClass.getName();
            UIFragment toFragment = (UIFragment) fm.findFragmentByTag(toTag);
            if (toFragment != null) {
                if (args != null) {
                    //toFragment.getArguments().putAll(args);
                    toFragment.setBundle(args);
                }
            } else {
                try {
                    toFragment = toFragmentClass.newInstance();
                    toFragment.setArguments(args);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        //隐藏现在的Fragment
        if(fromFragmentClass!=null) {
            UIFragment fromFragment = (UIFragment) fm.findFragmentByTag(fromFragmentClass.getName());
            if(!fromFragment.isHidden()){
                ft.hide(fromFragment);
                ft.commitAllowingStateLoss();
            }
        }
        fm.popBackStack();
    }


    /**
     * 弹出某个fragment上的所有栈中元素，并且显示那个栈
     * @param fromFragmentClass
     * @param toFragmentClass
     * @param args
     * @param fm
     * @param popState
     */
    public static void popFragment(Class<? extends UIFragment> fromFragmentClass,Class<? extends UIFragment> toFragmentClass,Bundle args,FragmentManager fm,int popState){
        FragmentTransaction ft = fm.beginTransaction();
        //设置fragment切换效果
        ft.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_left,R.animator.slide_in_right,R.animator.slide_out_right);
        //如果没有参数传递，那么不需要穿件toFragment
        //如果要切换的fragment不存在，那么创建
        String toTag = toFragmentClass.getName();
        if(args!=null) {
            //切换到fragment标签
            UIFragment toFragment = (UIFragment) fm.findFragmentByTag(toTag);
            if (toFragment != null) {
                if (args != null) {
                    //toFragment.getArguments().putAll(args);
                    toFragment.setBundle(args);
                }
            } else {
                try {
                    toFragment = toFragmentClass.newInstance();
                    toFragment.setArguments(args);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        //隐藏现在的Fragment
        if(fromFragmentClass!=null) {
            UIFragment fromFragment = (UIFragment) fm.findFragmentByTag(fromFragmentClass.getName());
            if(!fromFragment.isHidden()){
                ft.hide(fromFragment);
                ft.commitAllowingStateLoss();
            }
        }
        if(toTag!=null) {
            fm.popBackStackImmediate(toTag, popState);
        }else{
            fm.popBackStackImmediate();
        }
    }

}
