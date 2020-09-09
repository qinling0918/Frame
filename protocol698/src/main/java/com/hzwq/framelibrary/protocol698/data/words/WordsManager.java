package com.hzwq.framelibrary.protocol698.data.words;



/**
 * Created by qinling on 2019/1/17 16:41
 * Description: 状态字、特征字、模式字 管理者
 */
public class WordsManager {
 /*   private static final String[] RUNNING_STATUS_WORD =new String[]{
            "保留","需量积算方式","时钟电池","停电抄表电池","有功功率方向","无功功率方向","保留","保留",
            "控制回路","ESAM","时钟电池","停电抄表电池","有功功率方向","无功功率方向","保留","保留",
    };*/
  //  private SparseArray<SparseArray<Pair<String,String>>> words = new SparseArray<>();

    private static class SingleTon {
        private static WordsManager instance = new WordsManager();
    }

    public static WordsManager getInstance(/*int type*/) {
        return SingleTon.instance;
    }

    private WordsManager() {
        initWords();
    }

    /**
     * 每种数据类型的长度  （是字符串长度，不是字节数）
     */
    private void initWords() {
      // Pair<String,String> runningStatusWord = new


    }
}
