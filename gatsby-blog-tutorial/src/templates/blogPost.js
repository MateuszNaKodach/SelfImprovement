import React from 'react'
import { graphql, Link } from "gatsby"

const fontStyle = {
  fontFamily: 'avenir'
}

const Template = ({ data, pageContext }) => {
  const {next, prev} = pageContext;

  const { markdownRemark } = data
  const title = markdownRemark.frontmatter.title;
  const html = markdownRemark.html;
  return (
    <div>
      <h1 style={fontStyle}>{title}</h1>
      <div className='blogpost'
      dangerouslySetInnerHTML={{__html: html}}
           style={fontStyle}
      />

      {next && <Link to={next.frontmatter.path}>Next</Link>}
      {prev && <Link to={next.frontmatter.path}>Previous</Link>}

    </div>
  )
}



export const query = graphql`
  query($pathSlug: String!) {
    markdownRemark(frontmatter: {path: {eq: $pathSlug}}) {
      html
      frontmatter {
        title
      }
    }  
  }
`

export default Template;