package com.univocity.trader.indicators;

import com.univocity.trader.candles.*;
import com.univocity.trader.indicators.base.*;
import org.apache.commons.lang3.*;
import org.junit.*;

import java.util.function.*;

import static com.univocity.trader.candles.CandleHelper.*;
import static com.univocity.trader.indicators.AverageTrueRangeTest.*;
import static com.univocity.trader.indicators.base.TimeInterval.*;
import static junit.framework.TestCase.*;

public class MACDTest {

    @Test
    public void getValue() {
        MACD t = new MACD(3, 7, 2, minutes(1));

        for (int i = 0; i < prices.length; i++) {
            t.accumulate(newCandle(i, prices[i][2], prices[i][2], prices[i][0], prices[i][1]));
        }

        assertEquals(0.3231803444657828, t.getHistogram(), 0.0000001);
        assertEquals(9.643743725795236, t.getMacdSignal(), 0.0000001);
        assertEquals(9.966924070217999, t.getMacdLine(), 0.0000001);

    }

    public void accumulate(MACD indicator, int minute, double value, double macdLine, double macdSignal,
        double histogram) {
        CandleHelper.accumulate(indicator, newCandle(minute, value));
        if (indicator.getClass().equals(MACD.class)) {
            assertEquals(macdLine, indicator.getMacdLine(), 0.00001);
            assertEquals(macdSignal, indicator.getMacdSignal(), 0.00001);
            assertEquals(histogram, indicator.getHistogram(), 0.00001);
        }
    }

    @Test
    public void testDefaultMacd() {
        run(new MACD(12, 26, 9, minutes(1)));
    }

    @Test
    public void testMixedMacd() {
        run(new MACD(12, 26, 9, minutes(1)) {
            @Override
            protected SingleValueIndicator getSignalAverageIndicator(int macdCount, TimeInterval interval) {
                return new DoubleExponentialMovingAverage(macdCount, interval, null);
            }

            @Override
            protected SingleValueIndicator getShortAverageIndicator(int shortCount, TimeInterval interval,
                ToDoubleFunction<Candle> valueGetter) {
                return new MovingAverage(shortCount, interval, valueGetter);
            }
        });
    }

    @Test
    public void testSmaMacd() {
        run(new MACD(12, 26, 9, minutes(1)) {
            @Override
            protected SingleValueIndicator getAverageIndicator(int count, TimeInterval interval,
                ToDoubleFunction<Candle> valueGetter) {
                return new MovingAverage(count, interval, valueGetter);
            }
        });
    }

    @Test
    public void testDemaMacd() {
        run(new MACD(12, 26, 9, minutes(1)) {
            @Override
            protected SingleValueIndicator getAverageIndicator(int count, TimeInterval interval,
                ToDoubleFunction<Candle> valueGetter) {
                return new DoubleExponentialMovingAverage(count, interval, valueGetter);
            }
        });
    }

    @Test
    public void testMmaMacd() {
        run(new MACD(12, 26, 9, minutes(1)) {
            @Override
            protected SingleValueIndicator getAverageIndicator(int count, TimeInterval interval,
                ToDoubleFunction<Candle> valueGetter) {
                return new ModifiedMovingAverage(count, interval, valueGetter);
            }
        });
    }

    private void run(MACD t) {
        accumulate(t, 1, 7413.63000000, 0.0, 0.0, 0.0);
        accumulate(t, 2, 7415.55000000, 0.15316239316325664, 0.03063247863265133, 0.12252991453060531);
        accumulate(t, 3, 7414.81000000, 0.21238463973622856, 0.06698291085336677, 0.1454017288828618);
        accumulate(t, 4, 7418.94000000, 0.5858221874532319, 0.17075076617333979, 0.4150714212798921);
        accumulate(t, 5, 7420.67000000, 1.0097311244107914, 0.3385468378208301, 0.6711842865899613);
        accumulate(t, 6, 7420.02000000, 1.2784947516638567, 0.5265364205894354, 0.7519583310744213);
        accumulate(t, 7, 7417.69000000, 1.2886258584667303, 0.6789543081648943, 0.609671550301836);
        accumulate(t, 8, 7420.00000000, 1.4661516446121823, 0.8363937754543519, 0.6297578691578304);
        accumulate(t, 9, 7412.36000000, 0.9790718286913034, 0.8649293861017422, 0.11414244258956119);
        accumulate(t, 10, 7413.95000000, 0.7131366814173816, 0.8345708451648701, -0.1214341637474885);
        accumulate(t, 11, 7415.43000000, 0.6147186978669197, 0.7906004157052801, -0.17588171783836037);
        accumulate(t, 12, 7419.99000000, 0.8943659335718621, 0.8113535192785964, 0.08301241429326567);
        accumulate(t, 13, 7420.36000000, 1.132786235806634, 0.875640062584204, 0.25714617322243005);
        accumulate(t, 14, 7422.81000000, 1.5021151194860067, 1.0009350739645646, 0.5011800455214421);
        accumulate(t, 15, 7420.63000000, 1.6004541010643152, 1.1208388793845148, 0.4796152216798004);
        accumulate(t, 16, 7420.23000000, 1.6273526866189059, 1.222141640831393, 0.4052110457875129);
        accumulate(t, 17, 7427.99000000, 2.248913086279572, 1.4274959299210288, 0.8214171563585431);
        accumulate(t, 18, 7423.87000000, 2.38160099956076, 1.618316943848975, 0.763284055711785);
        accumulate(t, 19, 7424.00000000, 2.4687883846154364, 1.7884112320022674, 0.680377152613169);
        accumulate(t, 20, 7422.27000000, 2.3709575508592025, 1.9049204957736543, 0.46603705508554816);
        accumulate(t, 21, 7423.08000000, 2.3319053870800417, 1.9903174740349319, 0.3415879130451098);
        accumulate(t, 22, 7420.31000000, 2.0537658811326764, 2.0030071554544806, 0.05075872567819584);
        accumulate(t, 23, 7420.45000000, 1.8236136655759765, 1.9671284574787797, -0.14351479190280325);
        accumulate(t, 24, 7422.35000000, 1.7740800076271626, 1.9285187675084563, -0.15443875988129374);
        accumulate(t, 25, 7417.57000000, 1.333743608862278, 1.8095637357792207, -0.47582012691694264);
        accumulate(t, 26, 7423.45000000, 1.4426111755410602, 1.7361732237315886, -0.29356204819052834);
        accumulate(t, 27, 7424.52000000, 1.5968223895988558, 1.708303056905042, -0.11148066730618611);
        accumulate(t, 28, 7421.01000000, 1.419445735205045, 1.6505315925650426, -0.23108585735999765);
        accumulate(t, 29, 7417.45000000, 0.9803107981706489, 1.5164874336861638, -0.5361766355155149);
        accumulate(t, 30, 7418.67000000, 0.722409513619823, 1.3576718496728957, -0.6352623360530727);
        accumulate(t, 31, 7423.22000000, 0.8750804785604487, 1.2611535754504062, -0.3860730968899575);
        accumulate(t, 32, 7421.34000000, 0.834750545353927, 1.1758729694311103, -0.3411224240771833);
        accumulate(t, 33, 7420.80000000, 0.7505632695319946, 1.0908110294512872, -0.34024775991929257);
        accumulate(t, 34, 7420.45000000, 0.6481309221553602, 1.0022750079921017, -0.3541440858367415);
        accumulate(t, 35, 7418.92000000, 0.43844026911574474, 0.8895080602168303, -0.4510677911010855);
        accumulate(t, 36, 7419.73000000, 0.3337716347496098, 0.7783607751233862, -0.4445891403737764);
        accumulate(t, 37, 7418.82000000, 0.1753700364879478, 0.6577626273962985, -0.48239259090835074);
        accumulate(t, 38, 7419.67000000, 0.11707396905876521, 0.5496248957287919, -0.4325509266700267);
        accumulate(t, 39, 7419.23000000, 0.034966520570378634, 0.44669322069710926, -0.4117267001267306);
        accumulate(t, 40, 7419.76000000, 0.01251804644925869, 0.35985818584753915, -0.34734013939828046);
        accumulate(t, 41, 7417.55000000, -0.18150874149705487, 0.25158480037862035, -0.4330935418756752);
        accumulate(t, 42, 7416.94000000, -0.38011652417299047, 0.1252445354682982, -0.5053610596412886);
        accumulate(t, 43, 7415.68000000, -0.6319019205811855, -0.026184755741598564, -0.6057171648395869);
        accumulate(t, 44, 7413.93000000, -0.9615696594128167, -0.21326173647584218, -0.7483079229369745);
        accumulate(t, 45, 7410.52000000, -1.480921082606983, -0.46679360570207035, -1.0141274769049127);
        accumulate(t, 46, 7415.80000000, -1.449747171061972, -0.6633843187740507, -0.7863628522879214);
        accumulate(t, 47, 7415.41000000, -1.4399129421190082, -0.8186900434430422, -0.621222898675966);
        accumulate(t, 48, 7413.95000000, -1.532266034062559, -0.9614052415669455, -0.5708607924956134);
        accumulate(t, 49, 7411.85000000, -1.7546820914649288, -1.1200606115465421, -0.6346214799183867);
        accumulate(t, 50, 7414.35000000, -1.7095131097548801, -1.2379511111882098, -0.47156199856667036);
        accumulate(t, 51, 7410.47000000, -1.9641583450566031, -1.3831925579618884, -0.5809657870947147);
        accumulate(t, 52, 7409.39000000, -2.2274369955621296, -1.5520414454819367, -0.6753955500801929);
        accumulate(t, 53, 7412.05000000, -2.1961318129606298, -1.6808595189776754, -0.5152722939829544);
        accumulate(t, 54, 7418.25000000, -1.6519909227399694, -1.6750857997301343, 0.02309487699016488);
        accumulate(t, 55, 7416.26000000, -1.3655900438188837, -1.613186648547884, 0.24759660472900036);
        accumulate(t, 56, 7414.21000000, -1.2891723746488424, -1.5483837937680758, 0.2592114191192334);
        accumulate(t, 57, 7415.68000000, -1.097344601086661, -1.4581759552317928, 0.3608313541451318);
        accumulate(t, 58, 7415.02000000, -0.9871964069725436, -1.363980045579943, 0.3767836386073993);
        accumulate(t, 59, 7409.48000000, -1.331585184724645, -1.3575010734088833, 0.02591588868423833);
        accumulate(t, 60, 7412.81000000, -1.3205896666122499, -1.3501187920495565, 0.02952912543730668);

    }
}