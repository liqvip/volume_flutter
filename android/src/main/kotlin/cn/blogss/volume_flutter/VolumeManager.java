package cn.blogss.volume_flutter;

import android.content.Context;
import android.media.AudioManager;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 音量管理
 * create by Thatcher Li at 2021/05/10
 */

public class VolumeManager {

    private final String TAG = "VolumeManager";
    private final boolean OpenLog = true;

    private final AudioManager audioManager;
    private int NOW_AUDIO_TYPE = TYPE_MUSIC;
    private int NOW_FLAG = FLAG_SHOW_UI;
    private int VOICE_STEP = 2; //0-100的步进。
    private double maxVol = 100;

    /**
     * 封装：STREAM_类型
     */
    public final static int TYPE_SYSTEM = AudioManager.STREAM_SYSTEM;   // 系统音量
    public final static int TYPE_MUSIC = AudioManager.STREAM_MUSIC;     // 媒体音量
    public final static int TYPE_ALARM = AudioManager.STREAM_ALARM;
    public final static int TYPE_RING = AudioManager.STREAM_RING;
    @IntDef({TYPE_SYSTEM, TYPE_MUSIC, TYPE_ALARM, TYPE_RING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {}

    /**
     * 封装：FLAG
     */
    public final static int FLAG_SHOW_UI = AudioManager.FLAG_SHOW_UI;
    public final static int FLAG_PLAY_SOUND = AudioManager.FLAG_PLAY_SOUND;
    public final static int FLAG_NOTHING = 0;
    @IntDef({FLAG_SHOW_UI, FLAG_PLAY_SOUND, FLAG_NOTHING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FLAG {}

    /**
     * 初始化，获取音量管理者
     * @param context   上下文
     */
    public VolumeManager(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public double getSystemMaxVolume() {
        return (double) audioManager.getStreamMaxVolume(NOW_AUDIO_TYPE);
    }

    public double getSystemCurrentVolume() {
        return (double)audioManager.getStreamVolume(NOW_AUDIO_TYPE);
    }

    /**
     * 以0-maxVol为范围，获取当前的音量值
     * @return  获取当前的音量值
     */
    public double getCurrentVolume() {
        return maxVol*getSystemCurrentVolume()/getSystemMaxVolume();
    }

    /**
     * 修改步进值
     * @param step  step
     * @return  this
     */
    public VolumeManager setVoiceStep(int step) {
        VOICE_STEP = step;
        return this;
    }

    /**
     * 根据实际需要，设置最大音量
     * @param maxVol
     * @return
     */
    public VolumeManager setMaxVol(double maxVol){
        this.maxVol = maxVol;
        return this;
    }

    /**
     * 改变当前的模式，对全局API生效
     * @param type
     * @return
     */
    public VolumeManager setAudioType(@TYPE int type) {
        NOW_AUDIO_TYPE = type;
        return this;
    }

    /**
     * 改变当前FLAG，对全局API生效
     * @param flag
     * @return
     */
    public VolumeManager setFlag(@FLAG int flag) {
        NOW_FLAG = flag;
        return this;
    }

    public VolumeManager addVoiceSystem() {
        audioManager.adjustStreamVolume(NOW_AUDIO_TYPE,AudioManager.ADJUST_RAISE,NOW_FLAG);
        return this;
    }

    public VolumeManager subVoiceSystem() {
        audioManager.adjustStreamVolume(NOW_AUDIO_TYPE,AudioManager.ADJUST_LOWER,NOW_FLAG);
        return this;
    }

    /**
     * 调整音量，自定义
     * @param vol  0-maxVol
     * @return  改完后的音量值
     */
    public double setVoice(double vol) {
        int a =  (int)Math.ceil(vol/maxVol*getSystemMaxVolume());
        a = Math.max(a, 0);
        a = Math.min(a, 100);
        audioManager.setStreamVolume(NOW_AUDIO_TYPE,a,NOW_FLAG);
        return getCurrentVolume();
    }

    /**
     * 步进加，步进值可修改
     *  0——maxVol
     * @return  改完后的音量值
     */
    public double addVoiceStep() {
        int a = (int) Math.ceil((VOICE_STEP + getCurrentVolume())/maxVol*getSystemMaxVolume());
        a = Math.max(a, 0);
        a = Math.min(a, 100);
        audioManager.setStreamVolume(NOW_AUDIO_TYPE,a,NOW_FLAG);
        return getCurrentVolume();
    }

    /**
     * 步进减，步进值可修改
     *  0——maxVol
     * @return  改完后的音量值
     */
    public double subVoiceStep() {
        int a = (int) Math.floor((getCurrentVolume() - VOICE_STEP)*getSystemMaxVolume()*0.01);
        a = Math.max(a, 0);
        a = Math.min(a, 100);
        audioManager.setStreamVolume(NOW_AUDIO_TYPE,a,NOW_FLAG);
        return getCurrentVolume();
    }
}