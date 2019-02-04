<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                version="1.0"
                exclude-result-prefixes="xalan">

    <xsl:output method="xml" indent="yes" xalan:indent-amount="4" encoding="UTF-8"/>

    <xsl:template match="/entries">
        <xsl:text>&#xA;</xsl:text>
        <entries>
            <xsl:apply-templates select="entry"/>
        </entries>
    </xsl:template>

    <xsl:template match="entry">
        <xsl:for-each select="field">
            <entry field="{.}"/>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>