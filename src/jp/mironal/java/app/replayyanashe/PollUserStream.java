package jp.mironal.java.app.replayyanashe;

import java.util.ArrayList;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;



public class PollUserStream {

    private ArrayList<OnStatusListener> onStatusListeners =
             new ArrayList<OnStatusListener>();
             
    /**
     * OnStatus発生時に呼ばれるリスナを追加.
     * @param listener streamでonStatusが呼ばれた時に実行されるコールバック
     * @return {@link ArrayList#add(Object)}の戻り値.
     */
    public boolean addOnStatusListener( OnStatusListener listener ){
        if(listener == null){
            throw new NullPointerException("listener is null.");
        }
        return onStatusListeners.add(listener);
    }
    
    /**
     * OnStatus発生時に呼ばれるリスナを削除.
     * @param listener リスナ
     * @return {@link ArrayList#remove(Object)}の戻り値.
     */
    public boolean removeOnStatusListener( OnStatusListener listener ){
        if(listener == null){
            throw new NullPointerException("listener is null.");
        }
        
        return onStatusListeners.remove(listener);
    }
    
    private final TwitterStream stream = new TwitterStreamFactory()
            .getInstance();
    
    // 表示している情報は一部のため、必要であれば増やすことを検討する.
    private UserStreamListener listener = new UserStreamListener() {

        public void onStatus(Status status) {        
            for(OnStatusListener l : onStatusListeners){
                l.onStatus(status);
            }
        }

        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

        }

        public void onDeletionNotice(long directMessageId, long userId) {
            // ignore
        }

        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            // ignore
        }

        public void onScrubGeo(long userId, long upToStatusId) {
            // ignore
        }

        public void onFriendList(long[] friendIds) {
            // ignore
        }

        public void onFavorite(User source, User target, Status favoritedStatus) {
            // ignore
        }

        public void onUnfavorite(User source, User target,
                Status unfavoritedStatus) {
            // ignore
        }

        public void onFollow(User source, User followedUser) {
            // ignore
        }

        public void onRetweet(User source, User target, Status retweetedStatus) {
            // ignore
        }

        public void onDirectMessage(DirectMessage directMessage) {
            // ignore
        }

        public void onUserListMemberAddition(User addedMember, User listOwner,
                UserList list) {
            // ignore
        }

        public void onUserListMemberDeletion(User deletedMember,
                User listOwner, UserList list) {
            // ignore
        }

        public void onUserListSubscription(User subscriber, User listOwner,
                UserList list) {
            // ignore
        }

        public void onUserListUnsubscription(User subscriber, User listOwner,
                UserList list) {
            // ignore
        }

        public void onUserListCreation(User listOwner, UserList list) {
            // ignore
        }

        public void onUserListUpdate(User listOwner, UserList list) {
            // ignore
        }

        public void onUserListDeletion(User listOwner, UserList list) {
            // ignore
        }

        public void onUserProfileUpdate(User updatedUser) {
            // ignore
        }

        public void onBlock(User source, User blockedUser) {
            // ignore
        }

        public void onUnblock(User source, User unblockedUser) {
            // ignore
        }

        public void onException(Exception ex) {
            // ignore
        }
    };

    public PollUserStream start() {
        stream.addListener(listener);
        stream.user();
        return this;
    }

    public void stop() {
        stream.shutdown();
    }

}
