package cn.mxzf.liyun.platform.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * @Title: User
 * @Dscription: 用户
 * @author Deleter
 * @date 2017年6月26日 上午9:45:47
 * @version 1.0
 */
public class User implements Serializable
{
    private static final long serialVersionUID = 5498460413933297886L;

    private long userId;// ID

    private String username;// 用户名

    private String password;// 密码

    private String headImg;// 头像

    private String sign;// 个性签名

    private String nickname;// 昵称

    private int sex;// 性别

    private Date birthday;// 生日

    private String work;// 职业

    private String company;// 公司

    private String school;// 学校

    private String localtion;// 所在地

    private String country;// 故乡

    private String email;// 邮箱

    private String info;// 个人说明

    private String picwall;// 照片墙(arraylist的json)

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getHeadImg()
    {
        return headImg;
    }

    public void setHeadImg(String headImg)
    {
        this.headImg = headImg;
    }

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public Date getBirthday()
    {
        return birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public String getWork()
    {
        return work;
    }

    public void setWork(String work)
    {
        this.work = work;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public String getLocaltion()
    {
        return localtion;
    }

    public void setLocaltion(String localtion)
    {
        this.localtion = localtion;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public String getPicwall()
    {
        return picwall;
    }

    public void setPicwall(String picwall)
    {
        this.picwall = picwall;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((headImg == null) ? 0 : headImg.hashCode());
        result = prime * result + ((info == null) ? 0 : info.hashCode());
        result = prime * result + ((localtion == null) ? 0 : localtion.hashCode());
        result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((picwall == null) ? 0 : picwall.hashCode());
        result = prime * result + ((school == null) ? 0 : school.hashCode());
        result = prime * result + sex;
        result = prime * result + ((sign == null) ? 0 : sign.hashCode());
        result = prime * result + (int)(userId ^ (userId >>> 32));
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((work == null) ? 0 : work.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        User other = (User)obj;
        if (birthday == null)
        {
            if (other.birthday != null) return false;
        }
        else if (!birthday.equals(other.birthday)) return false;
        if (company == null)
        {
            if (other.company != null) return false;
        }
        else if (!company.equals(other.company)) return false;
        if (country == null)
        {
            if (other.country != null) return false;
        }
        else if (!country.equals(other.country)) return false;
        if (email == null)
        {
            if (other.email != null) return false;
        }
        else if (!email.equals(other.email)) return false;
        if (headImg == null)
        {
            if (other.headImg != null) return false;
        }
        else if (!headImg.equals(other.headImg)) return false;
        if (info == null)
        {
            if (other.info != null) return false;
        }
        else if (!info.equals(other.info)) return false;
        if (localtion == null)
        {
            if (other.localtion != null) return false;
        }
        else if (!localtion.equals(other.localtion)) return false;
        if (nickname == null)
        {
            if (other.nickname != null) return false;
        }
        else if (!nickname.equals(other.nickname)) return false;
        if (password == null)
        {
            if (other.password != null) return false;
        }
        else if (!password.equals(other.password)) return false;
        if (picwall == null)
        {
            if (other.picwall != null) return false;
        }
        else if (!picwall.equals(other.picwall)) return false;
        if (school == null)
        {
            if (other.school != null) return false;
        }
        else if (!school.equals(other.school)) return false;
        if (sex != other.sex) return false;
        if (sign == null)
        {
            if (other.sign != null) return false;
        }
        else if (!sign.equals(other.sign)) return false;
        if (userId != other.userId) return false;
        if (username == null)
        {
            if (other.username != null) return false;
        }
        else if (!username.equals(other.username)) return false;
        if (work == null)
        {
            if (other.work != null) return false;
        }
        else if (!work.equals(other.work)) return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "User [userId=" + userId + ", username=" + username + ", password=" + password
               + ", headImg=" + headImg + ", sign=" + sign + ", nickname=" + nickname + ", sex="
               + sex + ", birthday=" + birthday + ", work=" + work + ", company=" + company
               + ", school=" + school + ", localtion=" + localtion + ", country=" + country
               + ", email=" + email + ", info=" + info + ", picwall=" + picwall + "]";
    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public User()
    {

    }
}
