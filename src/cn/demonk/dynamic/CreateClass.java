package cn.demonk.dynamic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.android.DexFile;
import android.app.Application;

public class CreateClass {

    public static final String PACKAGE_NAME = "cn.demonk.dynamic";
    public static final String CLASS_NAME = "TestActivity";
    private ClassPool mPool;
    private CtClass mClass;

    public CreateClass(Application application) {
        mPool = ClassPool.getDefault(application.getApplicationContext());
        mClass = mPool.makeClass(PACKAGE_NAME + "." + CLASS_NAME);
    }

    public void create(String path) {
        try {
            CtClass activityClass = mPool.get("android.app.Activity");
            //            CtClass activityClass=mPool.get("java.lang.Object");
            mClass.setSuperclass(activityClass);

            mClass.addConstructor(createConstructor());
            mClass.addMethod(createMethod_OnCreate());

            writeToFile(path);
            dexToFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CtConstructor createConstructor() throws CannotCompileException {
        CtConstructor cons = new CtConstructor(null, mClass);
        cons.setModifiers(Modifier.PUBLIC);
        //        cons.setBody("{System.out.println(\" 调用构造器！\");}"); 
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        //        sb.append("android.util.Log log;\n");
        //        sb.append("log.d(\"demonk\",\"aaaaa\");\n");
        //        sb.append("android.util.Log.d(\"demonk\",\"aaaaa\");\n");
        sb.append("}");

        cons.setBody(sb.toString());
        return cons;
    }

    private CtMethod createMethod_OnCreate() throws CannotCompileException {
        StringBuilder sb = new StringBuilder();
        sb.append("protected void onCreate(android.os.Bundle saved){\n");
        //        sb.append("{\n");
        sb.append("super.onCreate(saved);\n");
        //        sb.append("setContentView(cn.demonk.dynamic.R.layout.activity_test);\n");
        sb.append("}\n");

        CtMethod method = CtMethod.make(sb.toString(), mClass);
        return method;
    }

    private void writeToFile(String path) throws CannotCompileException, IOException {
        //        String fullPath = path + File.separator + CLASS_NAME + ".class";
        mClass.writeFile(path);
    }

    private void dexToFile(String path) throws IOException, CannotCompileException {
        String fullPath = path + File.separator + CLASS_NAME + ".dex";
        DexFile df = new DexFile();
        df.addClass(path, PACKAGE_NAME, CLASS_NAME);
        df.writeFile(fullPath);
    }
}
