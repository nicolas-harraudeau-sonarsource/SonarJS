/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.javascript.rules;

import com.google.common.collect.ImmutableSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.javascript.JavaScriptLanguage;
import org.sonarsource.analyzer.commons.ExternalRuleLoader;

public class EslintRulesDefinition implements RulesDefinition {

  public static final String LINTER_KEY = "eslint";
  public static final String LINTER_NAME = "ESLint";

  private static final Set<String> ESLINT_PLUGINS = ImmutableSet.of(
    "angular",
    "core",
    "ember",
    "flowtype",
    "import",
    "jsx-a11y",
    "promise",
    "react",
    "vue"
  );

  private static final Map<String, ExternalRuleLoader> RULE_LOADERS = new HashMap<>();

  static {
    ESLINT_PLUGINS.forEach(plugin -> RULE_LOADERS.put(plugin, new ExternalRuleLoader(
      LINTER_KEY,
      LINTER_NAME,
      "org/sonar/l10n/javascript/rules/eslint/" + plugin + ".json",
      JavaScriptLanguage.KEY)));
  }

  private boolean externalIssuesSupported;

  public EslintRulesDefinition(boolean externalIssuesSupported) {
    this.externalIssuesSupported = externalIssuesSupported;
  }

  @Override
  public void define(Context context) {
    if (externalIssuesSupported) {
      RULE_LOADERS.forEach((s, externalRuleLoader) -> externalRuleLoader.createExternalRuleRepository(context));
    }
  }
}
