
package com.crypto.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CryptoResponse {

        @SerializedName("symbol")
        @Expose
        private String symbol;
        @SerializedName("baseAsset")
        @Expose
        private String baseAsset;
        @SerializedName("quoteAsset")
        @Expose
        private String quoteAsset;
        @SerializedName("openPrice")
        @Expose
        private String openPrice;
        @SerializedName("lowPrice")
        @Expose
        private String lowPrice;
        @SerializedName("highPrice")
        @Expose
        private String highPrice;
        @SerializedName("lastPrice")
        @Expose
        private String lastPrice;
        @SerializedName("volume")
        @Expose
        private String volume;
        @SerializedName("bidPrice")
        @Expose
        private String bidPrice;
        @SerializedName("askPrice")
        @Expose
        private String askPrice;
        @SerializedName("at")
        @Expose
        private Long at;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getBaseAsset() {
            return baseAsset;
        }

        public void setBaseAsset(String baseAsset) {
            this.baseAsset = baseAsset;
        }

        public String getQuoteAsset() {
            return quoteAsset;
        }

        public void setQuoteAsset(String quoteAsset) {
            this.quoteAsset = quoteAsset;
        }

        public String getOpenPrice() {
            return openPrice;
        }

        public void setOpenPrice(String openPrice) {
            this.openPrice = openPrice;
        }

        public String getLowPrice() {
            return lowPrice;
        }

        public void setLowPrice(String lowPrice) {
            this.lowPrice = lowPrice;
        }

        public String getHighPrice() {
            return highPrice;
        }

        public void setHighPrice(String highPrice) {
            this.highPrice = highPrice;
        }

        public String getLastPrice() {
            return lastPrice;
        }

        public void setLastPrice(String lastPrice) {
            this.lastPrice = lastPrice;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getBidPrice() {
            return bidPrice;
        }

        public void setBidPrice(String bidPrice) {
            this.bidPrice = bidPrice;
        }

        public String getAskPrice() {
            return askPrice;
        }

        public void setAskPrice(String askPrice) {
            this.askPrice = askPrice;
        }

        public Long getAt() {
            return at;
        }

        public void setAt(Long at) {
            this.at = at;
        }



}
