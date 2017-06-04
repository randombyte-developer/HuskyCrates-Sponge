package pw.codehusky.huskycrates.lang;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.serializer.TextSerializers;
import pw.codehusky.huskycrates.crate.VirtualCrate;
import pw.codehusky.huskycrates.crate.config.CrateRewardHolder;

public class SharedLangData {
    public String prefix;
    public String rewardMessage;
    public String rewardAnnounceMessage;
    public String noKeyMessage;
    private void defaults() {
        //"", ,,
        prefix = "";
        rewardMessage = "You won %a %R&r from a %C&r!";
        rewardAnnounceMessage = "&e%p just won %R&r&e from a %C&r!";
        noKeyMessage = "You need a %K&r to open this crate.";
        endings();
    }
    public SharedLangData(){
        defaults();
    }
    public void endings() {
        prefix += "&r";
        rewardMessage += "&r";
        rewardAnnounceMessage += "&r";
        noKeyMessage += "&r";
    }
    public SharedLangData(SharedLangData base, ConfigurationNode node){
        prefix = base.prefix;
        rewardMessage = base.rewardMessage;
        rewardAnnounceMessage = base.rewardAnnounceMessage;
        noKeyMessage = base.noKeyMessage;
        if(!node.getNode("prefix").isVirtual()){
            prefix = node.getNode("prefix").getString(prefix);
        }
        if(!node.getNode("rewardMessage").isVirtual()){
            rewardMessage = node.getNode("rewardMessage").getString(rewardMessage);
        }
        if(!node.getNode("rewardAnnounceMessage").isVirtual()){
            rewardAnnounceMessage = node.getNode("rewardAnnounceMessage").getString(rewardAnnounceMessage);
        }
        if(!node.getNode("noKeyMessage").isVirtual()){
            noKeyMessage = node.getNode("noKeyMessage").getString(noKeyMessage);
        }
        endings();
    }
    public SharedLangData(ConfigurationNode node){
        defaults(); //defaults, then do overrides.
        if(!node.getNode("prefix").isVirtual()){
            prefix = node.getNode("prefix").getString(prefix);
        }
        if(!node.getNode("rewardMessage").isVirtual()){
            rewardMessage = node.getNode("rewardMessage").getString(rewardMessage);
        }
        if(!node.getNode("rewardAnnounceMessage").isVirtual()){
            rewardAnnounceMessage = node.getNode("rewardAnnounceMessage").getString(rewardAnnounceMessage);
        }
        if(!node.getNode("noKeyMessage").isVirtual()){
            noKeyMessage = node.getNode("noKeyMessage").getString(noKeyMessage);
        }
        endings();
    }
    public SharedLangData(String prefix, String rewardMessage, String rewardAnnounceMessage, String noKeyMessage){
        this.prefix = prefix;
        this.rewardMessage = rewardMessage;
        this.rewardAnnounceMessage = rewardAnnounceMessage;
        this.noKeyMessage = noKeyMessage;
        endings();
    }
    public String formatter(String toFormat, String aOrAn, Player context, VirtualCrate vc, CrateRewardHolder rewardHolder){
        String formatted = toFormat;
        if(aOrAn != null)
            formatted = formatted.replaceAll("%a",aOrAn);
        if(rewardHolder != null) {
            formatted = formatted.replaceAll("%R", rewardHolder.getReward().getRewardName());
            formatted = formatted.replaceAll("%r", TextSerializers.FORMATTING_CODE.stripCodes(rewardHolder.getReward().getRewardName()));
        }
        if(vc != null) {
            formatted = formatted.replaceAll("%C", vc.displayName);
            formatted = formatted.replaceAll("%c", TextSerializers.FORMATTING_CODE.stripCodes(vc.displayName));
            formatted = formatted.replaceAll("%K",vc.displayName + " Key");
            formatted = formatted.replaceAll("%k",TextSerializers.FORMATTING_CODE.stripCodes(vc.displayName + " Key"));
        }
        if(context != null) {
            formatted = formatted.replaceAll("%P", context.getName());
            formatted = formatted.replaceAll("%p", TextSerializers.FORMATTING_CODE.stripCodes(context.getName()));
        }
        return formatted;
    }
}