import React from "react"
import { StaticQuery, graphql } from "gatsby"

const getSiteMetadata =
  graphql`
        query {
          site {
            siteMetadata{
              title
              description
            }
          }
        }
      `

const TitleAndDescription = ({data}) => {
  const title = data.site.siteMetadata.title;
  const description = data.site.siteMetadata.description;

  return (
    <div style={
      {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        fontFamily: 'avenir'
      }
    }>
      <h2 style={{marginBottom: 0}}>{title}</h2>
      <p style={{
        marginTop: 0,
        opacity: 0.5
      }}>{description}</p>
    </div>
  )
}

const Header = () => {
  return (
    <StaticQuery query={getSiteMetadata} render={data => <TitleAndDescription data={data}/>}/>
  )
}

export default Header;