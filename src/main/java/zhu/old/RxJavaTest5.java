package zhu.old;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class RxJavaTest5 {
    public static void main(String[] args) throws InterruptedException {

//        AtomicBoolean ok = new AtomicBoolean(false);

        // response
        var res = Single
                .<String>create(emitter -> {
                    while (true) {
                        System.out.println("res");
//                        System.out.println(ok.get());
//                        if (ok.get()) {
//                            emitter.onSuccess("world");
//                            break;
//                        }
                        System.out.println("world");
                    }
                }).subscribeOn(Schedulers.io());

        // request
        var req = Single.<String>create(emitter -> {
            System.out.println("req");
//            ok.set(true);
            System.out.println("hello");
            emitter.onSuccess("hello");
        });

//        Single.zip(req, res, (reqMsg, resMsg) -> {
//            System.out.println("req: " + reqMsg);
//            System.out.println("res: " + resMsg);
//            return "ok";
//        }).subscribe(result -> {
//            System.out.println("result: " + result);
//        });
//
//        Single.zip(res, req, (resMsg, reqMsg) -> {
//            System.out.println("req: " + reqMsg);
//            System.out.println("res: " + resMsg);
//            return "ok";
//        }).subscribe(result -> {
//            System.out.println("result: " + result);
//        });

//        TimeUnit.SECONDS.sleep(10);

        System.out.println("done");
    }
}
