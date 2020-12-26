package com.xian.demo;

import com.xian.demo.enums.Animal;
import com.xian.demo.face.ShoutService;
import com.xian.demo.manager.ShoutServiceManager;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 8:44 下午 2020/12/26
 */
public class Main {


    public static void main(String[] args) {
        ShoutService service = ShoutServiceManager.getInstance().getService( Animal.CAT.getAnimal() );
        service.shout();
        service = ShoutServiceManager.getInstance().getService(Animal.DOG.getAnimal());
        service.shout();
        service = ShoutServiceManager.getInstance().getService(Animal.MOW.getAnimal());
        service.shout();
    }
}
