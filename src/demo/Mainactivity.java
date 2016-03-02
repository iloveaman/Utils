
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.androidl.R;

public class Mainactivity extends Activity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
     RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

//		// improve performance if you know that changes in content
//		// do not change the size of the RecyclerView
//		 mRecyclerView.setHasFixedSize(true);

     //创建布局管理器
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(mLayoutManager);

    //初始化数据
    List<Bean> myDataset = new ArrayList<Bean>();

    myDataset.add(new Bean(Bean.Z_TYPE, "图片"));
    myDataset.add(new Bean(Bean.X_TYPE, "文字"));
    myDataset.add(new Bean(Bean.Y_TYPE, "按钮"));
    myDataset.add(new Bean(Bean.Z_TYPE, "图片"));
    myDataset.add(new Bean(Bean.X_TYPE, "shit"));
    myDataset.add(new Bean(Bean.X_TYPE, "我擦"));
    myDataset.add(new Bean(Bean.Z_TYPE, "图片"));
    myDataset.add(new Bean(Bean.Y_TYPE, "按钮"));
    myDataset.add(new Bean(Bean.Y_TYPE, "按钮"));
    myDataset.add(new Bean(Bean.X_TYPE, "文字"));
    //创建Adapter
    RecycleAdapter mAdapter = new RecycleAdapter(myDataset);
    mRecyclerView.setAdapter(mAdapter);

  }

}
