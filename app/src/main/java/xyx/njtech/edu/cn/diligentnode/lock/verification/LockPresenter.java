package xyx.njtech.edu.cn.diligentnode.lock.verification;

import java.util.List;

import xyx.njtech.edu.cn.diligentnode.lock.base.BasePresenter;
import xyx.njtech.edu.cn.diligentnode.utils.MD5Util;


public class LockPresenter extends BasePresenter<ILockView> implements ILockPresenter{

    @Override
    public boolean verifyPassword(List<Integer> passPositions, String password) {
        StringBuilder sb=new StringBuilder();
        for (Integer i:passPositions){
            sb.append(i.intValue());
        }
        String currentPassword= MD5Util.getMd5Value(sb.toString());
        if(currentPassword.equals(password)){
            mView.onSuccess();
            return true;
        } else{
            mView.onError();
            return false;
        }
    }
}
