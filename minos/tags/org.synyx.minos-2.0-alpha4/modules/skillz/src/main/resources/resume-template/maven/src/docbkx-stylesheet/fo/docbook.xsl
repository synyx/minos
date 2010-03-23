<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">

    <!-- Import standard docbook stylesheet -->
	<xsl:import href="http://docbook.sourceforge.net/release/xsl/current/fo/docbook.xsl"/>

    <!-- Paper type, no headers on blank pages, no double sided printing -->
    <xsl:param name="paper.type" select="'A4'"/>
    <xsl:param name="double.sided">0</xsl:param>
    <xsl:param name="headers.on.blank.pages">0</xsl:param>
    <xsl:param name="footers.on.blank.pages">0</xsl:param> 
 	
 	<!-- Margins -->
 	<xsl:param name="page.margin.inner">28mm</xsl:param>
 	<xsl:param name="page.margin.outer">28mm</xsl:param>
 	<xsl:param name="page.margin.top">24mm</xsl:param>
 	<xsl:param name="page.margin.bottom">24mm</xsl:param>
 	
 	<!-- Disable TOC -->
 	<xsl:param name="generate.toc">
        article nop
    </xsl:param>

    <!-- Prevent blank pages in output -->
    <xsl:template name="article.titlepage.before.verso">
    </xsl:template>
    <xsl:template name="article.titlepage.verso">
    </xsl:template>
    <xsl:template name="article.titlepage.separator">
    </xsl:template>
    
    <!-- Disable header and footer navigation -->
	<xsl:param name="header.rule">0</xsl:param>
	<xsl:param name="footer.rule">0</xsl:param>
	<xsl:template name="header.content"><fo:block></fo:block></xsl:template>
	<xsl:template name="footer.content"><fo:block></fo:block></xsl:template>
	
	<!-- No Autonumbering for sections -->
	<xsl:param name="section.autolabel">0</xsl:param>
	
	    <!-- Default Font size -->
    <xsl:param name="title.font.family">StellaLining</xsl:param>
    
    <xsl:param name="body.font.family">StellaLining</xsl:param>
    <xsl:param name="body.font.master">11</xsl:param>
    <xsl:param name="body.font.small">9</xsl:param>

	<!-- Put Only Table Title, Not Autonumbering (see XSL common/en.xml) -->
	<xsl:param name="local.l10n.xml" select="document('')"/>
	<l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0">
		<l:l10n language="en">
			<l:context name="title">
				<l:template name="table" text="%t"/>
			</l:context>
		</l:l10n>
	</l:i18n>
	
	<!-- No default draft image -->
	<xsl:param name="draft.watermark.image" select="''"/>
 
	<!-- Enable FOP entensions -->
	<xsl:param name="fop1.extensions" select="1"/>

	<!-- No indenting -->
	<xsl:param name="body.start.indent">0mm</xsl:param>

	<!-- Indent informaltable with class 'referenceattributes' -->
	<xsl:attribute-set name="informaltable.properties">
		<xsl:attribute name="start-indent">
    		<xsl:choose>
    		  <xsl:when test="@class='referenceattributes'">1mm</xsl:when>
    		  <xsl:otherwise>0mm</xsl:otherwise>
    		</xsl:choose>
  		</xsl:attribute>
	</xsl:attribute-set>
	
	<!-- Role for bold and italic font -->
	<xsl:template match="emphasis[@role='bold-italic']">
  		<fo:inline font-style="italic" font-weight="bold">
    		<xsl:apply-templates/>
  		</fo:inline>
	</xsl:template>
	
	<!-- Bigger font size on contact section -->
	<xsl:attribute-set name="section.properties">
		<xsl:attribute name="font-size">
			<xsl:choose>
    		 	<xsl:when test="@id='contact'">16</xsl:when>
    		</xsl:choose>
		</xsl:attribute>
	</xsl:attribute-set>
	
	<!-- Processing-instruction for hard page breaks -->
	<xsl:template match="processing-instruction('hard-pagebreak')">
		<fo:block break-after='page'/>
 	</xsl:template>
 	
 	<!-- Processing-instruction for hard line breaks -->
 	<xsl:template match="processing-instruction('hard-linebreak')">
		<fo:block/>
 	</xsl:template>

	<!-- Processing-instruction that keeps the all elements from the
	 enclosing element on the same page or pushes them to the next page
	 if there isn't enough space available -->
	<xsl:template match="*[processing-instruction('mansour-fo') = 'keep-together']">
		<fo:block keep-together.within-column="always">
			<xsl:apply-imports />
		</fo:block>
	</xsl:template>

	<!-- Bigger font size for section title with id 'name' -->
	<xsl:attribute-set name="section.title.level1.properties">
		<xsl:attribute name="font-size">
			<xsl:choose>
    		 	<xsl:when test="@id='name'">
    		 	    <xsl:value-of select="$body.font.master * 2.0736"></xsl:value-of>
    		 	</xsl:when>
    		 	<xsl:otherwise>
    		 		<xsl:value-of select="$body.font.master * 1.728"></xsl:value-of>
    		 	</xsl:otherwise>
    		</xsl:choose>
    		<xsl:text>pt</xsl:text>
  		</xsl:attribute>
	</xsl:attribute-set>

	<!-- Background image for every page -->
	<xsl:template name="header.table">
		<xsl:param name="pageclass" select="''" />
		<xsl:param name="sequence" select="''" />
		<xsl:param name="gentext-key" select="''" />

		<xsl:if test="$pageclass = 'body'">
			<fo:block-container absolute-position="absolute"
				top="-2.4cm" left="-2.8cm" width="21cm" height="29.7cm"
				background-image="images/background.png">
				<fo:block />
			</fo:block-container>
		</xsl:if>
	</xsl:template>
	
	<!-- colored and hyphenated links -->
    <xsl:template match="ulink">
        <fo:basic-link external-destination="{@url}" xsl:use-attribute-sets="xref.properties" text-decoration="underline" color="blue">
            <xsl:choose>
                <xsl:when test="count(child::node())=0">
                    <xsl:value-of select="@url"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:apply-templates/>
                </xsl:otherwise>
            </xsl:choose>
        </fo:basic-link>
    </xsl:template>

    <xsl:template match="link">
        <fo:basic-link internal-destination="{@linkend}" xsl:use-attribute-sets="xref.properties" text-decoration="underline" color="blue">
            <xsl:choose>
                <xsl:when test="count(child::node())=0">
                    <xsl:value-of select="@linkend"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:apply-templates/>
                </xsl:otherwise>
            </xsl:choose>
        </fo:basic-link>
    </xsl:template>

</xsl:stylesheet>