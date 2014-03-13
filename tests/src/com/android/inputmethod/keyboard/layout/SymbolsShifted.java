/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.inputmethod.keyboard.layout;

import com.android.inputmethod.keyboard.layout.LayoutBase.LayoutCustomizer;
import com.android.inputmethod.keyboard.layout.expected.ExpectedKey;
import com.android.inputmethod.keyboard.layout.expected.ExpectedKeyboardBuilder;
import com.android.inputmethod.keyboard.layout.expected.AbstractLayoutBase;

/**
 * The symbols shifted keyboard layout.
 */
public class SymbolsShifted extends AbstractLayoutBase {
    private final LayoutCustomizer mCustomizer;

    public SymbolsShifted(final LayoutCustomizer customizer) {
        mCustomizer = customizer;
    }

    public ExpectedKey[][] getLayout(final boolean isPhone) {
        final ExpectedKeyboardBuilder builder = new ExpectedKeyboardBuilder(isPhone
                ? toPhoneSymbolsShifted(SYMBOLS_SHIFTED_COMMON)
                : toTabletSymbolsShifted(SYMBOLS_SHIFTED_COMMON));
        builder.replaceKeyOfLabel(OTHER_CURRENCIES, mCustomizer.getOtherCurrencyKeys());
        return builder.build();
    }

    // Variations of the "other currencies" keys on the 2rd row.
    public static final String OTHER_CURRENCIES = "OTHER_CURRENCY";
    public static final ExpectedKey[] CURRENCIES_OTHER_THAN_DOLLAR = {
        Symbols.POUND_SIGN, Symbols.CENT_SIGN, Symbols.EURO_SIGN, Symbols.YEN_SIGN
    };
    public static final ExpectedKey[] CURRENCIES_OTHER_THAN_EURO = {
        Symbols.POUND_SIGN, Symbols.YEN_SIGN, key(Symbols.DOLLAR_SIGN, Symbols.CENT_SIGN),
        Symbols.CENT_SIGN
    };

    // Common symbols shifted keyboard layout.
    private static final ExpectedKey[][] SYMBOLS_SHIFTED_COMMON =
            new ExpectedKeyboardBuilder(10, 1 /* other_currencies */ + 5, 7, 5)
            // U+0060: "`" GRAVE ACCENT
            // U+2022: "•" BULLET
            // U+221A: "√" SQUARE ROOT
            // U+03C0: "π" GREEK SMALL LETTER PI
            // U+00F7: "÷" DIVISION SIGN
            // U+00D7: "×" MULTIPLICATION SIGN
            // U+00B6: "¶" PILCROW SIGN
            // U+2206: "∆" INCREMENT
            .setLabelsOfRow(1,
                    "~", "\u0060", "|", "\u2022", "\u221A",
                    "\u03C0", "\u00F7", "\u00D7", "\u00B6", "\u2206")
            // U+2022: "•" BULLET
            // U+266A: "♪" EIGHTH NOTE
            // U+2665: "♥" BLACK HEART SUIT
            // U+2660: "♠" BLACK SPADE SUIT
            // U+2666: "♦" BLACK DIAMOND SUIT
            // U+2663: "♣" BLACK CLUB SUIT
            .setMoreKeysOf("\u2022", "\u266A", "\u2665", "\u2660", "\u2666", "\u2663")
            // U+03C0: "π" GREEK SMALL LETTER PI
            // U+03A0: "Π" GREEK CAPITAL LETTER PI
            .setMoreKeysOf("\u03C0", "\u03A0")
            // U+00B6: "¶" PILCROW SIGN
            // U+00A7: "§" SECTION SIGN
            .setMoreKeysOf("\u00B6", "\u00A7")
            // U+00B0: "°" DEGREE SIGN
            .setLabelsOfRow(2, OTHER_CURRENCIES, "^", "\u00B0", "=", "{", "}")
            // U+2191: "↑" UPWARDS ARROW
            // U+2193: "↓" DOWNWARDS ARROW
            // U+2190: "←" LEFTWARDS ARROW
            // U+2192: "→" RIGHTWARDS ARROW
            .setMoreKeysOf("^", "\u2191", "\u2193", "\u2190", "\u2192")
            // U+00B0: "°" DEGREE SIGN
            // U+2032: "′" PRIME
            // U+2033: "″" DOUBLE PRIME
            .setMoreKeysOf("\u00B0", "\u2032", "\u2033")
            // U+2260: "≠" NOT EQUAL TO
            // U+2248: "≈" ALMOST EQUAL TO
            // U+221E: "∞" INFINITY
            .setMoreKeysOf("=", "\u2260", "\u2248", "\u221E")
            // U+00A9: "©" COPYRIGHT SIGN
            // U+00AE: "®" REGISTERED SIGN
            // U+2122: "™" TRADE MARK SIGN
            // U+2105: "℅" CARE OF
            .setLabelsOfRow(3,
                    "\\", "\u00A9", "\u00AE", "\u2122", "\u2105",
                    "[", "]")
            .setLabelsOfRow(4,
                    "<", ">", " ", ",", ".")
            // U+2039: "‹" SINGLE LEFT-POINTING ANGLE QUOTATION MARK
            // U+2264: "≤" LESS-THAN OR EQUAL TO
            // U+00AB: "«" LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
            .setMoreKeysOf("<", "\u2039", "\u2264", "\u00AB")
            // U+203A: "›" SINGLE RIGHT-POINTING ANGLE QUOTATION MARK
            // U+2265: "≥" GREATER-THAN EQUAL TO
            // U+00BB: "»" RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
            .setMoreKeysOf(">", "\u203A", "\u2265", "\u00BB")
            // U+2026: "…" HORIZONTAL ELLIPSIS
            .setMoreKeysOf(".", "\u2026")
            .build();

    private ExpectedKey[][] toPhoneSymbolsShifted(final ExpectedKey[][] common) {
        return new ExpectedKeyboardBuilder(common)
                .addKeysOnTheLeftOfRow(3, mCustomizer.getBackToSymbolsKey())
                .addKeysOnTheRightOfRow(3, DELETE_KEY)
                .addKeysOnTheLeftOfRow(4, mCustomizer.getAlphabetKey())
                .addKeysOnTheRightOfRow(4, key(ENTER_KEY, EMOJI_KEY))
                .build();
    }

    private ExpectedKey[][] toTabletSymbolsShifted(final ExpectedKey[][] common) {
        return new ExpectedKeyboardBuilder(common)
                // U+00BF: "¿" INVERTED QUESTION MARK
                // U+00A1: "¡" INVERTED EXCLAMATION MARK
                .addKeysOnTheRightOfRow(3,
                        key("\u00A1"), key("\u00BF"))
                .addKeysOnTheRightOfRow(1, DELETE_KEY)
                .addKeysOnTheRightOfRow(2, ENTER_KEY)
                .addKeysOnTheLeftOfRow(3, mCustomizer.getBackToSymbolsKey())
                .addKeysOnTheRightOfRow(3, mCustomizer.getBackToSymbolsKey())
                .addKeysOnTheLeftOfRow(4, mCustomizer.getAlphabetKey())
                .addKeysOnTheRightOfRow(4, EMOJI_KEY)
                .build();
    }

    public static class RtlSymbolsShifted extends SymbolsShifted {
        public RtlSymbolsShifted(final LayoutCustomizer customizer) {
            super(customizer);
        }

        @Override
        public ExpectedKey[][] getLayout(final boolean isPhone) {
            return new ExpectedKeyboardBuilder(super.getLayout(isPhone))
                .replaceKeyOfLabel("{", key("{", "}"))
                .replaceKeyOfLabel("}", key("}", "{"))
                .replaceKeyOfLabel("[", key("[", "]"))
                .replaceKeyOfLabel("]", key("]", "["))
                // U+2039: "‹" SINGLE LEFT-POINTING ANGLE QUOTATION MARK
                // U+2264: "≤" LESS-THAN OR EQUAL TO
                // U+00AB: "«" LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
                .replaceKeyOfLabel("<", key("<", ">",
                        moreKey("\u2039", "\u203A"), moreKey("\u2264", "\u2265"),
                        moreKey("\u00AB", "\u00BB")))
                // U+203A: "›" SINGLE RIGHT-POINTING ANGLE QUOTATION MARK
                // U+2265: "≥" GREATER-THAN EQUAL TO
                // U+00BB: "»" RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
                .replaceKeyOfLabel(">", key(">", "<",
                        moreKey("\u203A", "\u2039"), moreKey("\u2265", "\u2264"),
                        moreKey("\u00BB", "\u00AB")))
                .build();
        }
    }
}
