import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Card from '@material-ui/core/Card';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Link from '@material-ui/core/Link';
import Chip from '@material-ui/core/Chip';


export default function InfoCard() {
    var iconSrc = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2930274594,3307825079&fm=26&gp=0.jpg";
    var title = "苹果发布 iOS 13.5/iPadOS";
    var href = "https://www.ithome.com/0/488/331.htm";
    var keywords = ["华为", "hawei", "爱国"]

    const useStyles = makeStyles((theme) => ({
        infoCard: {
            maxWidth: 390,
            maxHeight: 100,
        },
        infoCard_icon: {
            maxWidth: 100,
            maxHeight: 100
        },
        title: {
            padding: theme.spacing(2),
        },
        infoCard_keywordChip: {
            width: 100,
            margin: theme.spacing(1),
        },
        keyword_container: {
            padding: theme.spacing(1),
        }

    }));
    const classes = useStyles();

    return (
        <Card className={classes.infoCard}>


            <CardMedia
                className={classes.infoCard_icon}
                component="img"
                alt=""
                height="140"
                image={iconSrc}
                title=""
            />

            <Typography variant="body1" >
                <Link
                    href={href}
                    color="inherit"
                    target="blank">
                    {title.length < 45 ? title : title.substr(0, 45) + "..."}
                </Link>
            </Typography>
            {keywords.map((item, index) => {
                if (index > 3) {
                    return null;
                }
                if (index === 3) {
                    return (<Chip
                        key={index}
                        className={classes.infoCard_keywordChip}
                        label="......"
                        variant="outlined"
                        color="primary"
                        clickable
                    />)
                }

                return (
                    <Chip
                        href={"#" + item}
                        component="a"
                        key={index}
                        className={classes.infoCard_keywordChip}
                        label={item}
                        variant="outlined"
                        color="primary"
                        clickable
                    />
                )
            })}
        </Card>
    )
}



