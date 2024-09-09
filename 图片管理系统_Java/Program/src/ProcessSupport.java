import javafx.application.Platform;

import java.util.function.IntPredicate;

public class ProcessSupport {
    private final IntPredicate predicate;
    private int logCnt = 0;
    private boolean ter = false;
    private int callCnt = 0;
    private static final int logN = 1;

    public ProcessSupport(IntPredicate predicate){
        this.predicate = predicate;
        dialog();
    }

    public void kill(){
        ter = true;
    }

    private void dialog(){
        while(logCnt < logN){
            Platform.runLater(this::poll);
            logCnt ++ ;
        }
    }

    private void poll(){
        logCnt -- ;
        if(!ter){
            ter = !predicate.test(callCnt ++ );
            if(!ter) dialog();
        }
    }
}
