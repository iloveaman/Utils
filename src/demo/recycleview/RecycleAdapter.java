import java.util.List;

import com.example.androidl.R;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Date : 2014/7/15
 * 
 * @author edsheng
 * 
 */
public class RecycleAdapter extends RecyclerView.Adapter<ViewHolder> {

  private List<Bean> beans;

  public RecycleAdapter(List<Bean> beans) {
    super();
    this.beans = beans;
  }

  /**
   * 内部TextHoler
   * 
   * @author edsheng
   * 
   */
  public class TextHoler extends RecyclerView.ViewHolder {
    public TextView textView;

    public TextHoler(View textview) {
      super(textview);
      this.textView = (TextView) textview.findViewById(R.id.mytext);
    }
  }

  /**
   * iamgeHolder
   * 
   * @author edsheng
   * 
   */
  public class ImageHoler extends RecyclerView.ViewHolder {
    public ImageView Imageview;

    public ImageHoler(View textview) {
      super(textview);
      this.Imageview = (ImageView) textview.findViewById(R.id.myiamge);
    }
  }

  /**
   * 按钮的holder
   * 
   * @author edsheng
   * 
   */
  public class ButtonHolder extends RecyclerView.ViewHolder {
    public Button button;

    public ButtonHolder(View textview) {
      super(textview);
      this.button = (Button) textview.findViewById(R.id.mybutton);
    }
  }

  @Override
  public int getItemCount() {
    // TODO Auto-generated method stub
    return beans.size();
  }

  /**
   * 获取消息的类型
   */
  @Override
  public int getItemViewType(int position) {
    // TODO Auto-generated method stub
    return beans.get(position).getType();
  }

  /**
   * 创建VIewHolder
   */
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
    // TODO Auto-generated method stub
    View v = null;
    ViewHolder holer = null;
    switch (viewtype) {
    case Bean.X_TYPE:
      v = LayoutInflater.from(parent.getContext()).inflate(
          R.layout.recylce_item_x, null);
      holer = new TextHoler(v);
      break;
    case Bean.Y_TYPE:
      v = LayoutInflater.from(parent.getContext()).inflate(
          R.layout.recylce_item_y, null);
      holer = new ButtonHolder(v);
      break;
    case Bean.Z_TYPE:
      v = LayoutInflater.from(parent.getContext()).inflate(
          R.layout.recylce_item_z, null);
      holer = new ImageHoler(v);
      break;
    }

    return holer;
  }

  /**
   * 绑定viewholder
   */
  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    // TODO Auto-generated method stub
    switch (getItemViewType(position)) {
    case Bean.X_TYPE:
      TextHoler textholer = (TextHoler) holder;
      textholer.textView.setText(beans.get(position).getText());
      break;
    case Bean.Y_TYPE:
      ButtonHolder buttonHolder = (ButtonHolder) holder;
      buttonHolder.button.setText(beans.get(position).getText());
      break;
    case Bean.Z_TYPE:
      ImageHoler imageHoler = (ImageHoler) holder;
      // imageHoler.Imageview.setImageResource(android.R.drawable.checkbox_on_background);
      break;
    }
  }
}
